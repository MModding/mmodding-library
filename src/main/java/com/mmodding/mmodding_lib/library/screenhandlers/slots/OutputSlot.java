package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;

public class OutputSlot extends RestrictedSlot {

	public OutputSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y, stack -> false);
	}
}
