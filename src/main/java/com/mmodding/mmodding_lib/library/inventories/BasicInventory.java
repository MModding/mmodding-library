package com.mmodding.mmodding_lib.library.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface BasicInventory extends Inventory {

	DefaultedList<ItemStack> getContent();

	static BasicInventory of(DefaultedList<ItemStack> content) {
		return () -> content;
	}

	static BasicInventory ofSize(int size) {
		return BasicInventory.of(DefaultedList.ofSize(size, ItemStack.EMPTY));
	}

	@Override
	default int size() {
		return this.getContent().size();
	}

	@Override
	default boolean isEmpty() {
		for (int index = 0; index < this.size(); index++) {
			ItemStack stack = this.getStack(index);
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	default ItemStack getStack(int slot) {
		return this.getContent().get(slot);
	}

	@Override
	default ItemStack removeStack(int slot, int amount) {
		ItemStack removed = Inventories.splitStack(this.getContent(), slot, amount);
		if (!removed.isEmpty()) {
			this.markDirty();
		}
		return removed;
	}

	@Override
	default ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.getContent(), slot);
	}

	@Override
	default void setStack(int slot, ItemStack stack) {
		this.getContent().set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
	}

	@Override
	default void clear() {
		this.getContent().clear();
	}

	@Override
	default void markDirty() {}

	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return true;
	}
}
