package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class ItemEnergy {

	private ItemEnergy() {}

	@Nullable
	public static EnergyStorage queryStorage(ItemStack stack) {
		return ItemEnergyImpl.ENERGY.find(stack, null);
	}

	/**
	 * Defines an energy storage for this item.
	 * @param item the item
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @param handler the storage query handler
	 */
	public static void defineEnergyStorage(Item item, long capacity, EnergyUnit unit, ItemEnergy.StorageQueryHandler handler) {
		ItemEnergyImpl.defineEnergyStorage(item, capacity, unit, handler);
	}

	public interface StorageQueryHandler {

		/**
		 * Handles a storage query with provided context.
		 * @param stack the item stack
		 * @param internalStorage the internal storage for the item instance
		 * @return the possible energy storage
		 */
		@Nullable
		EnergyStorage handle(ItemStack stack, EnergyStorage internalStorage);
	}
}
