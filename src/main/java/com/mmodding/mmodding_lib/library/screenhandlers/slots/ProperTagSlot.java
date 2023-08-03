package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

public class ProperTagSlot extends RestrictedSlot {

	public ProperTagSlot(Inventory inventory, int index, int x, int y, TagKey<Item> tagKey) {
		super(inventory, index, x, y, stack -> stack.isIn(tagKey));
	}
}
