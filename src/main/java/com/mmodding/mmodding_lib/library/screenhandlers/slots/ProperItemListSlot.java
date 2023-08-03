package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;

public class ProperItemListSlot extends RestrictedSlot {

	public ProperItemListSlot(Inventory inventory, int index, int x, int y, Item... items) {
		super(inventory, index, x, y, stack -> {
			for (Item item : items) {
				if (stack.isOf(item)) {
					return true;
				}
			}
			return false;
		});
	}
}
