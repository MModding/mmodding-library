package com.mmodding.library.block.api.catalog;

import com.mmodding.library.block.mixin.LeveledCauldronBlockAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Map;
import java.util.function.Predicate;

public class AdvancedCauldronBlock extends LeveledCauldronBlock {

	public AdvancedCauldronBlock(Settings settings, Predicate<Biome.Precipitation> predicate, Map<Item, CauldronBehavior> map) {
		super(settings, predicate, map);
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) {
		return ((LeveledCauldronBlockAccessor) this).getPrecipitationPredicate() != null && super.canBeFilledByDripstone(fluid);
	}

	@Override
	public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
		if (((LeveledCauldronBlockAccessor) this).getPrecipitationPredicate() != null) {
			super.precipitationTick(state, world, pos, precipitation);
		}
	}
}
