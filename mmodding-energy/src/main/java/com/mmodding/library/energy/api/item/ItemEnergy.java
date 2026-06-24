package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

public final class ItemEnergy {

	private ItemEnergy() {}

	/**
	 * Queries an {@link EnergyStorage} in a specified stack.
	 * @param stack the stack
	 * @return the possibly found energy storage
	 */
	@Nullable
	public static EnergyStorage queryStorage(ItemStack stack) {
		return ItemEnergyImpl.ENERGY.find(stack, null);
	}

	/**
	 * Accesses the {@link EnergyStorage} associated to the given {@link ItemStack}.
	 * <br><b>This should only be used to define the behavior of your item.</b>
	 * Otherwise, use {@link ItemEnergy#queryStorage(ItemStack)}.
	 * <br>By such, you can assume that as long you follow these guidelines, and that
	 * the item has a proper energy definition, the result will be non-null.
	 * @param accessKey the access key
	 * @param stack the stack
	 * @return the energy storage
	 */
	public static EnergyStorage accessStorage(AccessKey accessKey, ItemStack stack) {
		return ItemEnergyImpl.accessStorage(accessKey, stack);
	}

	/**
	 * Defines an energy storage for this item.
	 * @param item the item
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @param handler the storage query handler
	 */
	public static AccessKey defineEnergyStorage(Item item, long capacity, EnergyUnit unit, ItemEnergy.StorageQueryHandler handler) {
		return ItemEnergyImpl.defineEnergyStorage(item, capacity, unit, handler);
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

	/**
	 * An object which identifies (through identity checks) the propriety of an item (type) to its instances' storages.
	 * <br>It is collected from the definition, and used to access the matching instances' storages directly.
	 */
	public static class AccessKey {

		@ApiStatus.Internal
		public AccessKey() {}
	}
}
