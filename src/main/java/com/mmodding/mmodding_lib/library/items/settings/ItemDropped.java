package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface ItemDropped {

	void apply(ItemStack stack, World world, PlayerEntity user, ItemEntity droppedItem);
}
