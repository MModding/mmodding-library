package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.List;
import java.util.function.Consumer;

public class ResourceHandlerContainer {

	public static DataGeneratorEntrypoint createDummyEntrypoint(Consumer<FabricDynamicRegistryProvider.Entries> consumer) {
		return generator -> {
			FabricDataGenerator.Pack pack = generator.createPack();
			pack.addProvider((output, future) -> new ResourceHandler(output, future, consumer));
		};
	}

	public static EntrypointContainer<DataGeneratorEntrypoint> createEntrypointContainer(String namespace, Consumer<FabricDynamicRegistryProvider.Entries> consumer) {
		DataGeneratorEntrypoint entrypoint = ResourceHandlerContainer.createDummyEntrypoint(consumer);
		return new EntrypointContainer<>() {

			@Override
			public DataGeneratorEntrypoint getEntrypoint() {
				return entrypoint;
			}

			@Override
			public ModContainer getProvider() {
				return FabricLoader.getInstance().getModContainer(namespace).orElseThrow();
			}
		};
	}
}
