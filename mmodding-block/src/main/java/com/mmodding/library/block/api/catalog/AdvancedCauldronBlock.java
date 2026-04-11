package com.mmodding.library.block.api.catalog;

import com.mmodding.library.block.mixin.LeveledCauldronBlockAccessor;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class AdvancedCauldronBlock extends LayeredCauldronBlock {

	public AdvancedCauldronBlock(Properties settings, Predicate<Biome.Precipitation> predicate, Map<Item, CauldronInteraction> map) {
		super(settings, predicate, map);
	}

	@Override
	protected boolean canReceiveStalactiteDrip(Fluid fluid) {
		return ((LeveledCauldronBlockAccessor) this).getFillPredicate() != null && super.canReceiveStalactiteDrip(fluid);
	}

	@Override
	public void handlePrecipitation(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation) {
		if (((LeveledCauldronBlockAccessor) this).getFillPredicate() != null) {
			super.handlePrecipitation(state, world, pos, precipitation);
		}
	}
}
