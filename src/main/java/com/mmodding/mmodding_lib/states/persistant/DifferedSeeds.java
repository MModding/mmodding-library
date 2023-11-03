package com.mmodding.mmodding_lib.states.persistant;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class DifferedSeeds extends PersistentState {

	private final GeneratorOptionsDuckInterface generatorOptions;

	public DifferedSeeds(GeneratorOptionsDuckInterface generatorOptions) {
		this.generatorOptions = generatorOptions;
		this.markDirty();
	}

	public DifferedSeeds readNbt(NbtCompound nbt) {
		this.generatorOptions.mmodding_lib$fillDimensionSeedAddendsNbt(nbt.getList("dimension_seed_addends", NbtElement.COMPOUND_TYPE));
		return this;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("dimension_seed_addends", this.dimensionSeedAddendsToNbt());
		return nbt;
	}

	public NbtList dimensionSeedAddendsToNbt() {
		NbtList nbtList = new NbtList();

		MModdingGlobalMaps.getDifferedDimensionSeeds().forEach(worldKey -> {
			if (this.generatorOptions.mmodding_lib$containsDimensionSeedAddend(worldKey)) {
				NbtCompound nbt = new NbtCompound();
				nbt.putString("dimension", worldKey.getValue().toString());
				nbt.putLong("differed", this.generatorOptions.mmodding_lib$getDimensionSeedAddend(worldKey));
				nbtList.add(nbt);
			}
		});

		return nbtList;
	}
}
