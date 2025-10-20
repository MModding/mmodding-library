package com.mmodding.library.item.api.catalog;

import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public abstract class ItemAcceptingStacks extends Item {

	public ItemAcceptingStacks(Settings settings) {
		super(settings);
	}

	@Override
	public boolean onStackClicked(ItemStack thisStack, Slot otherSlot, ClickType clickType, PlayerEntity player) {
		if (clickType != ClickType.RIGHT) {
			return false;
		} else {
			ItemStack itemStack = otherSlot.getStack();
			if (this.getFilter().check(itemStack.getItem())) {
				boolean sound = otherSlot.getStack().getCount() > 0;
				this.receiveStack(thisStack, otherSlot.takeStack(1));
				if (sound) {
					this.playReceiveStackSound(player);
				}
			}

			return true;
		}
	}

	@Override
	public boolean onClicked(ItemStack thisStack, ItemStack otherStack, Slot thisSlot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && thisSlot.canTakePartial(player)) {
			if (this.getFilter().check(otherStack.getItem())) {
				boolean sound = otherStack.getCount() > 0;
				if (sound) {
					this.receiveStack(thisStack, otherStack);
					otherStack.decrement(otherStack.getCount());
					this.playReceiveStackSound(player);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public abstract void receiveStack(ItemStack currentStack, ItemStack receivedStack);

	public abstract void playReceiveStackSound(PlayerEntity player);

	public abstract FilterList<Item> getFilter();
}
