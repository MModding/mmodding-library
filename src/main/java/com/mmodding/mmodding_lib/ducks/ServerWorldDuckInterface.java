package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.states.persistant.StellarStatuses;
import net.minecraft.nbt.NbtCompound;

public interface ServerWorldDuckInterface {

	StellarStatuses mmodding_lib$createStellarStatuses();

	StellarStatuses mmodding_lib$stellarStatusesFromNbt(NbtCompound nbt);

	StellarStatuses mmodding_lib$getStellarStatuses();
}
