package com.mmodding.library.item.api.catalog;

import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ItemAcceptingStacks extends Item {

	public ItemAcceptingStacks(Properties settings) {
		super(settings);
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack thisStack, Slot otherSlot, ClickAction clickType, Player player) {
		if (clickType != ClickAction.SECONDARY) {
			return false;
		} else {
			ItemStack itemStack = otherSlot.getItem();
			if (this.getFilter().check(itemStack.getItem())) {
				boolean sound = otherSlot.getItem().getCount() > 0;
				this.receiveStack(thisStack, otherSlot.remove(1));
				if (sound) {
					this.playReceiveStackSound(player);
				}
			}

			return true;
		}
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack otherStack, Slot thisSlot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
		if (clickType == ClickAction.SECONDARY && thisSlot.allowModification(player)) {
			if (this.getFilter().check(otherStack.getItem())) {
				boolean sound = otherStack.getCount() > 0;
				if (sound) {
					this.receiveStack(thisStack, otherStack);
					otherStack.shrink(otherStack.getCount());
					this.playReceiveStackSound(player);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public abstract void receiveStack(ItemStack currentStack, ItemStack receivedStack);

	public abstract void playReceiveStackSound(Player player);

	public abstract FilterList<Item> getFilter();
}
