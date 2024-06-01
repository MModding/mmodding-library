package com.mmodding.library.core.api.management;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.*;
import com.mmodding.library.core.api.management.context.RegistryContext;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.registry.Registry;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ElementsManager {

	public final BiList<RegistryContext, ContentHolderProvider> dynamics = BiList.create();

	public final List<Supplier<DefaultContentHolder>> defaults = new ArrayList<>();

	public final List<ContentHolder> instances = new ArrayList<>();

	private final ManagerSide side;

	public boolean sealed = false;

	private ElementsManager(ManagerSide side) {
		this.side = side;
	}

	@SuppressWarnings("unchecked")
	public <R extends ContentHolder> R fetchClass(Class<R> type) {
		AtomicReference<R> value = new AtomicReference<>();
		this.instances.forEach(registrable -> {
			if (registrable.getClass() == type) {
				value.set((R) registrable);
			}
		});
		return value.get();
	}

	private AdvancedContainer retrieveContainer(String modId) {

		String key = switch (this.side) {
			case CLIENT -> "client";
			case COMMON -> "main";
			case SERVER -> "server";
		};

		Class<?> type = switch (this.side) {
			case CLIENT -> ClientModInitializer.class;
			case COMMON -> ModInitializer.class;
			case SERVER -> DedicatedServerModInitializer.class;
		};

		AdvancedContainer container = null;

		for (EntrypointContainer<?> entrypointContainer : FabricLoader.getInstance().getEntrypointContainers(key, type)) {
			if (Objects.equals(entrypointContainer.getProvider().getMetadata().getId(), modId)) {
				container = AdvancedContainer.of(entrypointContainer.getProvider());
			}
		}

		return container;
	}

	public void initDynamicContent(String modId, List<Registry<?>> registries) {
		AdvancedContainer mod = this.retrieveContainer(modId);
		this.dynamics.forEach((context, provider) -> {
			ContentHolder holder = context.transform(mod, registries, provider);
			this.instances.add(holder);
			context.transfer(mod, registries, holder);
		});
	}

	public void initDefaultContent(AdvancedContainer mod) {
		this.defaults.forEach(supplier -> {
			DefaultContentHolder registrable = supplier.get();
			this.instances.add(registrable);
			registrable.register(mod);
		});
	}

	public static class Builder {

		private final BiList<RegistryContext, ContentHolderProvider> dynamics = BiList.create();

		private final List<Supplier<DefaultContentHolder>> defaults = new ArrayList<>();

		private final ManagerSide side;

		private Builder(ManagerSide side) {
			this.side = side;
		}

		public static Builder client() {
			return new Builder(ManagerSide.CLIENT);
		}

		public static Builder common() {
			return new Builder(ManagerSide.COMMON);
		}

		public static Builder server() {
			return new Builder(ManagerSide.SERVER);
		}

		public <T> ElementsManager.Builder ifModLoadedWith(String modId, RegistryContext context, SimpleContentHolder.Provider<T> provider) {
			if (FabricLoader.getInstance().isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public <L, R> ElementsManager.Builder ifModLoadedWith(String modId, RegistryContext context, DoubleContentHolder.Provider<L, R> provider) {
			if (FabricLoader.getInstance().isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public ElementsManager.Builder ifModLoadedWith(String modId, RegistryContext context, MultipleContentHolder.Provider provider) {
			if (FabricLoader.getInstance().isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public <T> ElementsManager.Builder withRegistry(RegistryContext context, SimpleContentHolder.Provider<T> provider) {
			this.dynamics.add(context, provider);
			return this;
		}

		public <L, R> ElementsManager.Builder withRegistry(RegistryContext context, DoubleContentHolder.Provider<L, R> provider) {
			this.dynamics.add(context, provider);
			return this;
		}

		public ElementsManager.Builder withRegistry(RegistryContext context, MultipleContentHolder.Provider provider) {
			this.dynamics.add(context, provider);
			return this;
		}

		@SafeVarargs
		public final ElementsManager.Builder withDefaults(Supplier<DefaultContentHolder>... defaults) {
			this.defaults.addAll(List.of(defaults));
			return this;
		}

		public ElementsManager build() {
			ElementsManager manager = new ElementsManager(this.side);
			manager.dynamics.addAll(this.dynamics);
			manager.defaults.addAll(this.defaults);
			return manager;
		}
	}

	public enum ManagerSide {
		CLIENT,
		COMMON,
		SERVER
	}
}
