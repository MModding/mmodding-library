package com.mmodding.library.core.api.management;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.*;
import com.mmodding.library.core.api.management.context.RegistryContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.apache.commons.lang3.tuple.Pair;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.entrypoint.EntrypointContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ElementsManager {

	public final List<Pair<? extends RegistryContext, ? extends ContentHolderProvider>> dynamics = new ArrayList<>();

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
			case CLIENT -> "client_init";
			case COMMON -> "init";
			case SERVER -> "server_init";
		};

		Class<?> type = switch (this.side) {
			case CLIENT -> ClientModInitializer.class;
			case COMMON -> ModInitializer.class;
			case SERVER -> DedicatedServerModInitializer.class;
		};

		AdvancedContainer container = null;

		for (EntrypointContainer<?> entrypointContainer : QuiltLoader.getEntrypointContainers(key, type)) {
			if (Objects.equals(entrypointContainer.getProvider().metadata().id(), modId)) {
				container = AdvancedContainer.of(entrypointContainer.getProvider());
			}
		}

		return container;
	}

	public void initDynamicContent(String modId, Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries) {
		AdvancedContainer mod = this.retrieveContainer(modId);
		this.dynamics.forEach(pair -> {
			RegistryContext context = pair.getKey();
			ContentHolderProvider provider = pair.getValue();
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

		private final List<Pair<? extends RegistryContext, ? extends ContentHolderProvider>> dynamics = new ArrayList<>();

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
			if (QuiltLoader.isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public <L, R> ElementsManager.Builder ifModLoadedWith(String modId, RegistryContext context, DoubleContentHolder.Provider<L, R> provider) {
			if (QuiltLoader.isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public ElementsManager.Builder ifModLoadedWith(String modId, RegistryContext context, MultipleContentHolder.Provider provider) {
			if (QuiltLoader.isModLoaded(modId)) {
				this.withRegistry(context, provider);
			}
			return this;
		}

		public <T> ElementsManager.Builder withRegistry(RegistryContext context, SimpleContentHolder.Provider<T> provider) {
			this.dynamics.add(Pair.of(context, provider));
			return this;
		}

		public <L, R> ElementsManager.Builder withRegistry(RegistryContext context, DoubleContentHolder.Provider<L, R> provider) {
			this.dynamics.add(Pair.of(context, provider));
			return this;
		}

		public ElementsManager.Builder withRegistry(RegistryContext context, MultipleContentHolder.Provider provider) {
			this.dynamics.add(Pair.of(context, provider));
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
