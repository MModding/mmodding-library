package com.mmodding.mmodding_lib.library.fluids.buckets;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public interface BucketManager {

	BucketManager DEFAULT = new BucketManager() {

		@Override
		public ItemStack getEmptiedItem(ItemStack stack) {
			return new ItemStack(Items.BUCKET);
		}

		@Override
		public ItemStack getFilledItem(ItemStack stack) {
			return stack;
		}
	};

	ItemStack getEmptiedItem(ItemStack stack);

	ItemStack getFilledItem(ItemStack stack);

	default boolean safeMode() {
		return false;
	}
}
