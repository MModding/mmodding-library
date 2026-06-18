package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.block.BlockEnergyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

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
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @param handler the storage query handler
	 */
	public static void defineEnergyStorage(Block block, long capacity, EnergyUnit unit, StorageQueryHandler handler) {
		BlockEnergyImpl.defineEnergyStorage(block, capacity, unit, handler);
	}

	public interface StorageQueryHandler {

		/**
		 * Handles a storage query with provided context.
		 * @param level the level
		 * @param pos the block position
		 * @param side the side
		 * @param internalStorage the internal storage for the block instance
		 * @return the possible energy storage
		 */
		@Nullable
		EnergyStorage handle(ServerLevel level, BlockPos pos, Direction side, EnergyStorage internalStorage);
	}
}
