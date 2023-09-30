package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import net.minecraft.entity.Entity;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(Entity.class)
public interface EntitySyncableDataRegistry {

	default SyncableData.Registry getSyncableDataRegistry() {
		throw new AssertionError();
	}
}
