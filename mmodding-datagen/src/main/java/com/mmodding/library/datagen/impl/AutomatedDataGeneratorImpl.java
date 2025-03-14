package com.mmodding.library.datagen.impl;

import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import com.mmodding.library.datagen.api.DataBehavior;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ApiStatus.Internal
public class AutomatedDataGeneratorImpl {

	public static final Map<DataBehavior, Set<Class<?>>> REGISTRY = new Object2ObjectOpenHashMap<>();

	public static void provideDataGenerators(List<EntrypointContainer<DataGeneratorEntrypoint>> entrypointContainers) {
		for (Map.Entry<DataBehavior, Set<Class<?>>> entry : AutomatedDataGeneratorImpl.REGISTRY.entrySet()) {
			for (Class<?> clazz : entry.getValue()) {
				DataContainers containers = DataContainers.retrieveFrom(clazz);
				entrypointContainers.add(DatagenContainerCallback.createDummyEntrypoint(DataProcessor.process(containers), entry.getKey().namespace()));
			}
		}
	}
}
