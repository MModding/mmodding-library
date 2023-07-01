package com.mmodding.mmodding_lib.persistentstates;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;
import net.minecraft.world.gen.GeneratorOptions;

public class DifferedSeedsState extends PersistentState {

	private final GeneratorOptions generatorOptions;

	public DifferedSeedsState(GeneratorOptions generatorOptions) {
		this.generatorOptions = generatorOptions;
	}

	private GeneratorOptionsDuckInterface ducked() {
		return (GeneratorOptionsDuckInterface) this.generatorOptions;
	}

	public DifferedSeedsState readNbt(NbtCompound nbt) {
		this.ducked().fillDimensionSeedAddendsNbt(nbt.getList("dimensionSeedAddends", NbtElement.COMPOUND_TYPE));
		return this;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("dimensionSeedAddends", this.dimensionSeedAddendsToNbt());
		return null;
	}

	public NbtList dimensionSeedAddendsToNbt() {
		NbtList nbtList = new NbtList();

		MModdingGlobalMaps.getDifferedDimensionSeeds().forEach(worldKey -> {
			if (this.ducked().containsDimensionSeedAddend(worldKey)) {
				NbtCompound nbt = new NbtCompound();
				nbt.putString("dimensionIdentifier", worldKey.getValue().toString());
				nbt.putLong("differed", this.ducked().getDimensionSeedAddend(worldKey));
				nbtList.add(nbt);
			}
		});

		return nbtList;
	}
}
