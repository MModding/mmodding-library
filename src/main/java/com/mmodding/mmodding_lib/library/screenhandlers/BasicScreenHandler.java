package com.mmodding.mmodding_lib.library.screenhandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class BasicScreenHandler extends ScreenHandler {

	protected final Inventory inventory;

	protected BasicScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int inventorySize) {
		super(type, syncId);
		this.inventory = inventory;
		ScreenHandler.checkSize(this.inventory, inventorySize);
		this.inventory.onOpen(playerInventory.player);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);

		if (slot.hasStack()) {
			ItemStack slotStack = slot.getStack();
			itemStack = slotStack.copy();

			if (fromIndex < this.inventory.size()) {
				if (!this.insertItem(slotStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(slotStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return itemStack;
	}
}
