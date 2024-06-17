package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.DataBehavior;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ApiStatus.Internal
public class AutomatedDataGeneratorImpl {

	public static final Map<DataBehavior, Set<Class<?>>> REGISTRY = new Object2ObjectOpenHashMap<>();

	public static List<EntrypointContainer<DataGeneratorEntrypoint>> provideDataGenerators(List<EntrypointContainer<DataGeneratorEntrypoint>> entrypointContainers) {
		for (Map.Entry<DataBehavior, Set<Class<?>>> entry : AutomatedDataGeneratorImpl.REGISTRY.entrySet()) {
			for (Class<?> clazz : entry.getValue()) {
				DataContainers containers = DataContainers.retrieveFrom(clazz);
				entrypointContainers.add(new EntrypointContainerImpl(DataProcessor.process(containers), entry.getKey().namespace()));
			}
		}
		return entrypointContainers;
	}

	private static class EntrypointContainerImpl implements EntrypointContainer<DataGeneratorEntrypoint> {

		private final DataGeneratorEntrypoint entrypoint;
		private final String namespace;

		private EntrypointContainerImpl(DataGeneratorEntrypoint entrypoint, String namespace) {
			this.entrypoint = entrypoint;
			this.namespace = namespace;
		}

		@Override
		public DataGeneratorEntrypoint getEntrypoint() {
			return this.entrypoint;
		}

		@Override
		public ModContainer getProvider() {
			return FabricLoader.getInstance().getModContainer(this.namespace).orElseThrow();
		}
	}
}
