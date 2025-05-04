package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.entity.Entity;

@ClassExtension(Entity.class)
public interface EntitySyncableDataRegistry {

	default SyncableData.Registry getSyncableDataRegistry() {
		throw new AssertionError();
	}
}
