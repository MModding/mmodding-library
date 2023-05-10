package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ItemPostMine {

	void apply(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner);
}
