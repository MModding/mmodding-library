package com.mmodding.mmodding_lib.library.screenhandlers.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

public class ProperTagListSlot extends RestrictedSlot {

	@SafeVarargs
	public ProperTagListSlot(Inventory inventory, int index, int x, int y, TagKey<Item>... tagKeys) {
		super(inventory, index, x, y, stack -> {
			for (TagKey<Item> tagKey : tagKeys) {
				if (stack.isIn(tagKey)) {
					return true;
				}
			}
			return false;
		});
	}
}
