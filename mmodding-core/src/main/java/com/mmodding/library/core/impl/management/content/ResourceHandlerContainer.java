package com.mmodding.library.core.impl.management.content;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.JsonKeySortOrderCallback;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.core.RegistrySetBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ResourceHandlerContainer {

	public static DataGeneratorEntrypoint createEntrypoint(Consumer<FabricDynamicRegistryProvider.Entries> consumer, @Nullable DataGeneratorEntrypoint wrapped) {
		if (wrapped != null) {
			return new DataGeneratorEntrypoint() {

				@Override
				public void onInitializeDataGenerator(FabricDataGenerator generator) {
					FabricDataGenerator.Pack pack = generator.createPack();
					pack.addProvider((output, future) -> new ResourceHandler(output, future, consumer));
					wrapped.onInitializeDataGenerator(generator);
				}

				@Override
				@Nullable
				public String getEffectiveModId() {
					return wrapped.getEffectiveModId();
				}

				@Override
				public void buildRegistry(RegistrySetBuilder registryBuilder) {
					wrapped.buildRegistry(registryBuilder);
				}

				@Override
				public void addJsonKeySortOrders(JsonKeySortOrderCallback callback) {
					wrapped.addJsonKeySortOrders(callback);
				}
			};
		}
		else {
			return generator -> {
				FabricDataGenerator.Pack pack = generator.createPack();
				pack.addProvider((output, future) -> new ResourceHandler(output, future, consumer));
			};
		}
	}

	public static EntrypointContainer<DataGeneratorEntrypoint> createEntrypointContainer(String namespace, Consumer<FabricDynamicRegistryProvider.Entries> consumer, @Nullable DataGeneratorEntrypoint wrapped) {
		DataGeneratorEntrypoint entrypoint = ResourceHandlerContainer.createEntrypoint(consumer, wrapped);
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
