package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.utils.InternalOf;
import com.mmodding.mmodding_lib.states.persistant.StellarStatuses;
import net.minecraft.nbt.NbtCompound;

@InternalOf(StellarStatuses.class)
public interface ServerWorldDuckInterface {

	StellarStatuses mmodding_lib$createStellarStatuses();

	StellarStatuses mmodding_lib$stellarStatusesFromNbt(NbtCompound nbt);

	StellarStatuses mmodding_lib$getStellarStatuses();
}
