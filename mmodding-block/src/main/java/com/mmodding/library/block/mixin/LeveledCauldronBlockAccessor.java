package com.mmodding.library.block.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;

@Mixin(LayeredCauldronBlock.class)
public interface LeveledCauldronBlockAccessor {

	@Accessor
	Biome.Precipitation getPrecipitationType();
}
