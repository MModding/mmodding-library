package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface ItemPostHit {

	void apply(ItemStack stack, LivingEntity target, LivingEntity attacker);
}
