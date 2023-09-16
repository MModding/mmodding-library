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

	ItemStack getFilledItem(ItemStack stack);

	ItemStack getEmptiedItem(ItemStack stack);

	default ItemStack getFilledItemOrElse(ItemStack stack, ItemStack or) {
		ItemStack check = this.getFilledItem(stack);
		return !check.isEmpty() ? check : or;
	}

	default ItemStack getEmptiedItemOrElse(ItemStack stack, ItemStack or) {
		ItemStack check = this.getEmptiedItem(stack);
		return !check.isEmpty() ? check : or;
	}

	default ItemStack getFilledItemOrDefault(ItemStack stack) {
		return this.getFilledItemOrElse(stack, stack);
	}

	default ItemStack getEmptiedItemOrDefault(ItemStack stack) {
		return this.getEmptiedItemOrElse(stack, stack);
	}
}
