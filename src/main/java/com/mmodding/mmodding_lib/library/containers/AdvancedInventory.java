package com.mmodding.mmodding_lib.library.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class AdvancedInventory extends SimpleInventory {

	private final List<InventoryInteractionListener> inventoryOpenedListeners = new ArrayList<>();

	private final List<InventoryInteractionListener> inventoryClosedListeners = new ArrayList<>();

	private final boolean nestedFiltered;

	public AdvancedInventory(int size) {
		this(false, size);
	}

	public AdvancedInventory(ItemStack... stacks) {
		this(false, stacks);
	}

	public AdvancedInventory(boolean nestedFiltered, int size) {
		super(size);
		this.nestedFiltered = nestedFiltered;
	}

	public AdvancedInventory(boolean nestedFiltered, ItemStack... stacks) {
		super(stacks);
		this.nestedFiltered = nestedFiltered;
	}

	/**
	 * @return a boolean that indicates if the {@link AdvancedInventory} Instance filters items with {@link Item#canBeNested()}
	 */
	public boolean isNestedFiltered() {
		return this.nestedFiltered;
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

	public void readSortedNbtList(NbtList nbtList) {
		for(int i = 0; i < this.size(); ++i) {
			this.setStack(i, ItemStack.EMPTY);
		}

		for(int i = 0; i < nbtList.size(); ++i) {
			NbtCompound nbt = nbtList.getCompound(i);
			int slotIndex = nbt.getByte("Slot") & 255;
			if (slotIndex < this.size()) {
				this.setStack(slotIndex, ItemStack.fromNbt(nbt));
			}
		}
	}

	public NbtList toSortedNbtList() {
		NbtList nbtList = new NbtList();

		for(int i = 0; i < this.size(); ++i) {
			ItemStack itemStack = this.getStack(i);
			if (!itemStack.isEmpty()) {
				NbtCompound nbtCompound = new NbtCompound();
				nbtCompound.putByte("Slot", (byte) i);
				itemStack.writeNbt(nbtCompound);
				nbtList.add(nbtCompound);
			}
		}

		return nbtList;
	}

	public interface InventoryInteractionListener {

		void onInventoryInteracted(PlayerEntity player, Inventory inventory);
	}
}
