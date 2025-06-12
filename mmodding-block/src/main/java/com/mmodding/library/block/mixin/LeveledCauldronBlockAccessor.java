package com.mmodding.library.block.mixin;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

@Mixin(LeveledCauldronBlock.class)
public interface LeveledCauldronBlockAccessor {

	@Accessor
	Predicate<Biome.Precipitation> getPrecipitationPredicate();
}
