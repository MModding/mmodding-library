package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.item.ItemUsageContext;

@FunctionalInterface
public interface ItemUseOnBlock {

	void apply(ItemUsageContext context);
}
