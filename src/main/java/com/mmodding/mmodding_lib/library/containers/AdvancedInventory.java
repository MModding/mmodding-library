package com.mmodding.mmodding_lib.library.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AdvancedInventory extends SimpleInventory {

	private final List<InventoryInteractionListener> inventoryOpenedListeners = new ArrayList<>();

	private final List<InventoryInteractionListener> inventoryClosedListeners = new ArrayList<>();

	public AdvancedInventory(int size) {
		super(size);
	}

	public AdvancedInventory(ItemStack... stacks) {
		super(stacks);
	}

	public void addInventoryOpenedListener(InventoryInteractionListener listener) {
		this.inventoryOpenedListeners.add(listener);
	}

	public void removeInventoryOpenedListener(InventoryInteractionListener listener) {
		this.inventoryOpenedListeners.remove(listener);
	}

	public void addInventoryClosedListener(InventoryInteractionListener listener) {
		this.inventoryClosedListeners.add(listener);
	}

	public void removeInventoryClosedListener(InventoryInteractionListener listener) {
		this.inventoryClosedListeners.remove(listener);
	}

	@Override
	public void onOpen(PlayerEntity player) {
		this.inventoryOpenedListeners.forEach(listener -> listener.onInventoryInteracted(player, this));
	}

	@Override
	public void onClose(PlayerEntity player) {
		this.inventoryClosedListeners.forEach(listener -> listener.onInventoryInteracted(player, this));
	}

	public interface InventoryInteractionListener {

		void onInventoryInteracted(PlayerEntity player, Inventory inventory);
	}
}
