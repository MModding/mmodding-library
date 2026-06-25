package com.mmodding.library.energy.api.item;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public final class ItemEnergy {

	private ItemEnergy() {}

	/**
	 * Queries an {@link EnergyAccess} in a specified stack.
	 * @param stack the stack
	 * @return the possibly found energy access
	 */
	@Nullable
	public static EnergyAccess query(ItemStack stack) {
		return ItemEnergyImpl.ENERGY.find(stack, null);
	}

	/**
	 * Accesses the {@link EnergyComponent} associated to the given {@link ItemStack}.
	 * <br><b>This should only be used to define the behavior of your item.</b>
	 * Otherwise, use {@link ItemEnergy#query(ItemStack)}.
	 * <br>By such, you can assume that as long you follow these guidelines, and that
	 * the item has a proper energy definition, the result will be non-null.
	 * @param stack the stack
	 * @return the energy storage
	 */
	public static EnergyComponent retrieveFrom(ItemStack stack) {
		return ItemEnergyImpl.retrieveFrom(stack);
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
		 * @param context the container item context
		 * @param internalStorage the internal storage for the item instance
		 * @return the possible energy storage
		 */
		@Nullable
		EnergyAccess handle(ItemStack stack, ContainerItemContext context, EnergyComponent internalStorage);
	}
}
