package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface ItemUseOnEntity {

	void apply(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand);
}
