package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class ResourceGeneratorContainer implements EntrypointContainer<DataGeneratorEntrypoint> {

	private final String namespace;
	private final ResourceGenerator generator;

	public static void prepareResources(AdvancedContainer mod, ElementsManagerImpl manager) {
		DatagenContainerCallback.EVENT.register(containers -> {
			for (int i = 0; i < containers.size(); i++) { // Lookup for optionally existing data generator.
				EntrypointContainer<DataGeneratorEntrypoint> container = containers.get(i);
				if (container.getProvider().getMetadata().getId().equals(mod.getMetadata().getId())) {
					containers.set(i, new ResourceGeneratorContainer(
						mod.getMetadata().getId(),
						new ResourceGenerator(mod, manager.getBootstraps(), containers.get(i).getEntrypoint())
					));
					return; // Found! Wrapped! Done!
				}
			}
			containers.add(new ResourceGeneratorContainer(
				mod.getMetadata().getId(),
				new ResourceGenerator(mod, manager.getBootstraps(), null)
			)); // Nothing Wrapped!
		});
	}

	public ResourceGeneratorContainer(String namespace, ResourceGenerator generator) {
		this.namespace = namespace;
		this.generator = generator;
	}

	@Override
	public ResourceGenerator getEntrypoint() {
		return this.generator;
	}

	@Override
	public ModContainer getProvider() {
		return FabricLoader.getInstance().getModContainer(this.namespace).orElseThrow();
	}
}
