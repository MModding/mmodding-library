package com.mmodding.library.core.impl.registry.data;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.JsonKeySortOrderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public interface DatagenContainerCallback {

	Event<Identifier, DatagenContainerCallback> EVENT = MModdingLibrary.getEventManager().create(DatagenContainerCallback.class);

	static DataGeneratorEntrypoint createDummyEntrypoint(Consumer<RegistryBuilder> consumer, @Nullable DataGeneratorEntrypoint wrapped) {
		if (wrapped != null) {
			return new DataGeneratorEntrypoint() {

				@Override
				public @Nullable String getEffectiveModId() {
					return wrapped.getEffectiveModId();
				}

				@Override
				public void buildRegistry(RegistryBuilder registryBuilder) {
					consumer.accept(registryBuilder);
					wrapped.buildRegistry(registryBuilder);
				}

				@Override
				public void addJsonKeySortOrders(JsonKeySortOrderCallback callback) {
					wrapped.addJsonKeySortOrders(callback);
				}

				@Override
				public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
					wrapped.onInitializeDataGenerator(fabricDataGenerator);
				}
			};
		}
		else {
			return new DataGeneratorEntrypoint() {

				@Override
				public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {}

				@Override
				public void buildRegistry(RegistryBuilder registryBuilder) {
					consumer.accept(registryBuilder);
				}
			};
		}
	}

	static EntrypointContainer<DataGeneratorEntrypoint> createDummyEntrypointContainer(String namespace, Consumer<RegistryBuilder> consumer, @Nullable DataGeneratorEntrypoint wrapped) {
		DataGeneratorEntrypoint entrypoint = DatagenContainerCallback.createDummyEntrypoint(consumer, wrapped);
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

	void modifyContainers(List<EntrypointContainer<DataGeneratorEntrypoint>> containers);
}
