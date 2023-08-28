package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.mixin.accessors.LeveledCauldronBlockAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomCauldronBlock extends LeveledCauldronBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
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

	@Override
    public BlockItem getItem() {
        return null;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
