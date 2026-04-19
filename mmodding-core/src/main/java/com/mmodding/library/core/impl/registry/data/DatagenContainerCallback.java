package com.mmodding.library.core.impl.registry.data;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.resources.Identifier;

import java.util.List;

public interface DatagenContainerCallback {

	Event<Identifier, DatagenContainerCallback> EVENT = MModdingLibrary.getEventManager().create(DatagenContainerCallback.class);

	void modifyContainers(List<EntrypointContainer<DataGeneratorEntrypoint>> containers);
}
