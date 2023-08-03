package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class RestrictedSlot extends Slot {

	private final Predicate<ItemStack> filter;

	public RestrictedSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> filter) {
		super(inventory, index, x, y);
		this.filter = filter;
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return this.filter.test(stack);
	}
}
