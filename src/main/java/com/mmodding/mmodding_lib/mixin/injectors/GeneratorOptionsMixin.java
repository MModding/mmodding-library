package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.persistentstates.DifferedSeedsState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin implements GeneratorOptionsDuckInterface {

	@Unique
	public final Map<RegistryKey<World>, Long> dimensionSeedAddends = new HashMap<>();

	@Override
	public void mmodding_lib$fillDimensionSeedAddendsNbt(NbtList list) {
		for (int i = 0; i < list.size(); i++) {
			NbtCompound nbt = list.getCompound(i);
			String dimension = nbt.getString("dimension");
			long differed = nbt.getLong("differed");
			this.mmodding_lib$addDimensionSeedAddend(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dimension)), differed);
		}
	}

	@Override
	public boolean mmodding_lib$containsDimensionSeedAddend(RegistryKey<World> worldKey) {
		return this.dimensionSeedAddends.containsKey(worldKey);
	}

	@Override
	public void mmodding_lib$addDimensionSeedAddend(RegistryKey<World> worldKey, long differed) {
		this.dimensionSeedAddends.put(worldKey, differed);
	}

	@Override
	public long mmodding_lib$getDimensionSeedAddend(RegistryKey<World> worldKey) {
		return this.dimensionSeedAddends.get(worldKey);
	}

	@Override
	public DifferedSeedsState mmodding_lib$createDifferedSeedsState() {
		return new DifferedSeedsState(this);
	}

	@Override
	public DifferedSeedsState mmodding_lib$differedSeedsStateFromNbt(NbtCompound nbt) {
		return this.mmodding_lib$createDifferedSeedsState().readNbt(nbt);
	}
}
