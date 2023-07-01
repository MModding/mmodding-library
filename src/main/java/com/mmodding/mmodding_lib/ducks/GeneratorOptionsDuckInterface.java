package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.persistentstates.DifferedSeedsState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public interface GeneratorOptionsDuckInterface {

	void fillDimensionSeedAddendsNbt(NbtList list);

	boolean containsDimensionSeedAddend(RegistryKey<World> worldKey);

	void addDimensionSeedAddend(RegistryKey<World> worldKey, long differed);

	long getDimensionSeedAddend(RegistryKey<World> worldKey);

	DifferedSeedsState createDifferedSeedsState();

	DifferedSeedsState stateFromNbt(NbtCompound nbt);
}
