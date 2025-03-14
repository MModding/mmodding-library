package com.mmodding.library.core.impl.management;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.content.BootstrapProvider;
import com.mmodding.library.core.api.management.content.ContentProvider;
import com.mmodding.library.core.impl.management.content.BootstrapFunctionImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class ElementsManagerImpl implements ElementsManager {

	private final List<ContentProvider> contentProviders = new ArrayList<>();
	private final BiList<RegistryKey<Registry<?>>, BootstrapProvider<?>> bootstrapProviders = BiList.create();

	public void loadElements(AdvancedContainer mod) {
		this.contentProviders.forEach(provider -> provider.register(mod));
	}

	public void loadBootstraps(AdvancedContainer mod, RegistryBuilder builder) {
		this.bootstrapProviders.forEach((key, bootstrap) -> this.loadBootstrap(mod, builder, key, bootstrap));
	}

	@SuppressWarnings("unchecked")
	public <T> void loadBootstrap(AdvancedContainer mod, RegistryBuilder builder, RegistryKey<?> key, BootstrapProvider<?> bootstrap) {
		builder.addRegistry(
			(RegistryKey<? extends Registry<T>>) key,
			new BootstrapFunctionImpl<>(mod, (BootstrapProvider<T>) bootstrap)
		);
	}

	public static class Builder implements ElementsManager.Builder {

		private final List<ContentProvider> contentProviders = new ArrayList<>();
		private final BiList<RegistryKey<Registry<?>>, BootstrapProvider<?>> bootstrapProviders = BiList.create();

		@Override
		public ElementsManagerImpl.Builder ifModLoaded(String modId, ContentProvider provider) {
			if (FabricLoader.getInstance().isModLoaded(modId)) {
				this.content(provider);
			}
			return this;
		}

		@Override
		public ElementsManagerImpl.Builder content(ContentProvider provider) {
			this.contentProviders.add(provider);
			return this;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> ElementsManagerImpl.Builder data(RegistryKey<Registry<T>> key, BootstrapProvider<T> provider) {
			if (System.getProperty("fabric-api.datagen") != null) {
				this.bootstrapProviders.add((RegistryKey<Registry<?>>) (Registry<?>) key, (BootstrapProvider<?>) provider);
			}
			return this;
		}

		public ElementsManagerImpl build() {
			ElementsManagerImpl manager = new ElementsManagerImpl();
			manager.contentProviders.addAll(this.contentProviders);
			return manager;
		}
	}
}
