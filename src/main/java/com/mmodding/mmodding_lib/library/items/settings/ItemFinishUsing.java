package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ItemFinishUsing {

	ItemStack apply(ItemStack stack, World world, LivingEntity user);
}
