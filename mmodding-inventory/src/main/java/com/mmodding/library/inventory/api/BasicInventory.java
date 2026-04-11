package com.mmodding.library.inventory.api;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * A very basic {@link Container} implementation.
 */
public interface BasicInventory extends Container {

	NonNullList<ItemStack> getContent();

	static BasicInventory of(NonNullList<ItemStack> content) {
		return () -> content;
	}

	static BasicInventory ofSize(int size) {
		return BasicInventory.of(NonNullList.withSize(size, ItemStack.EMPTY));
	}

	@Override
	default int getContainerSize() {
		return this.getContent().size();
	}

	@Override
	default boolean isEmpty() {
		for (int index = 0; index < this.getContainerSize(); index++) {
			ItemStack stack = this.getItem(index);
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	default ItemStack getItem(int slot) {
		return this.getContent().get(slot);
	}

	@Override
	default ItemStack removeItem(int slot, int amount) {
		ItemStack removed = ContainerHelper.removeItem(this.getContent(), slot, amount);
		if (!removed.isEmpty()) {
			this.setChanged();
		}
		return removed;
	}

	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.getContent(), slot);
	}

	@Override
	default void setItem(int slot, ItemStack stack) {
		this.getContent().set(slot, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
	}

	@Override
	default void clearContent() {
		this.getContent().clear();
	}

	@Override
	default void setChanged() {}

	@Override
	default boolean stillValid(Player player) {
		return true;
	}
}
