package com.mmodding.library.core.impl.registry.data;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.util.Identifier;

import java.util.List;

public interface DatagenContainerCallback {

	Event<Identifier, DatagenContainerCallback> EVENT = MModdingLibrary.getEventManager().create(DatagenContainerCallback.class);

	static EntrypointContainer<DataGeneratorEntrypoint> createDummyEntrypoint(DataGeneratorEntrypoint entrypoint, String namespace) {
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
