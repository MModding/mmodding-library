package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.block.BlockEnergyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public final class BlockEnergy {

	private BlockEnergy() {}

	/**
	 * Queries an {@link EnergyStorage} in a specified level, at a specified block position, and for a specified side.
	 * @param level the level
	 * @param pos the block position
	 * @param side the side
	 * @return the possibly found energy storage
	 */
	@Nullable
	public static EnergyStorage queryStorage(ServerLevel level, BlockPos pos, Direction side) {
		return BlockEnergyImpl.SIDED.find(level, pos, side);
	}

	/**
	 * Defines an identifier energy storage for this block.
	 * @param block the block
	 * @param name the storage name
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @apiNote The storage name <b>must be unique</b>, and should follow the <code>snake_case</code> convention.
	 */
	public static void defineEnergyStorage(Block block, String name, long capacity, EnergyUnit unit) {
		BlockEnergyImpl.defineEnergyStorage(block, name, capacity, unit);
	}

	/**
	 * Defines a storage selector for this block.
	 * @apiNote Some blocks can not have an energy storage but still define a selector, for redirections in example.
	 * @param block the block
	 * @param selector the selector
	 * @throws IllegalStateException if the current block already has a defined selector
	 */
	public static void defineStorageSelector(Block block, StorageSelector selector) {
		BlockEnergyImpl.defineStorageSelector(block, selector);
	}

	public interface StorageSelector {

		/**
		 * Selects an energy storage when the block is queried.
		 * @param level the level
		 * @param pos the block position
		 * @param side the side
		 * @param definedStorages the defined storages for the current block
		 * @return the possible energy storage
		 */
		@Nullable
		EnergyStorage select(ServerLevel level, BlockPos pos, Direction side, Function<String, EnergyStorage> definedStorages);
	}
}
