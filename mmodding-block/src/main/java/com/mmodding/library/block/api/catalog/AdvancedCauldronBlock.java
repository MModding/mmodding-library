package com.mmodding.library.block.api.catalog;

import com.mmodding.library.block.mixin.LeveledCauldronBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class AdvancedCauldronBlock extends LayeredCauldronBlock {

	public AdvancedCauldronBlock(Biome.Precipitation precipitation, CauldronInteraction.Dispatcher interactionMap, Properties properties) {
		super(precipitation, interactionMap, properties);
	}

	@Override
	protected boolean canReceiveStalactiteDrip(Fluid fluid) {
		return ((LeveledCauldronBlockAccessor) this).getPrecipitationType() != null && super.canReceiveStalactiteDrip(fluid);
	}

	@Override
	public void handlePrecipitation(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation) {
		if (((LeveledCauldronBlockAccessor) this).getPrecipitationType() != null) {
			super.handlePrecipitation(state, world, pos, precipitation);
		}
	}
}
