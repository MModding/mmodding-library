package com.mmodding.library.block.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;

@Mixin(LayeredCauldronBlock.class)
public interface LeveledCauldronBlockAccessor {

	@Accessor
	Predicate<Biome.Precipitation> getFillPredicate();
}
