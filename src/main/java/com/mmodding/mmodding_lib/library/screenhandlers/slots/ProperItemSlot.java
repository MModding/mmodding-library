package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;

public class ProperItemSlot extends RestrictedSlot {

	public ProperItemSlot(Inventory inventory, int index, int x, int y, Item item) {
		super(inventory, index, x, y, stack -> stack.isOf(item));
	}
}
