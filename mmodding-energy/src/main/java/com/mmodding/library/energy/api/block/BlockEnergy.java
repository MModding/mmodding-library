package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.block.BlockEnergyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
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
	 * Accesses the {@link EnergyStorage} associated to the given {@link BlockEntity}.
	 * <br>You can see a block entity as a block "instance"; the purpose of this method is, by such, to give access
	 * to the storage associated to the block entity, only when defining its behavior through its implementation.
	 * To look up and interact with other storages you will <b>always use</b>
	 * {@link BlockEnergy#queryStorage(ServerLevel, BlockPos, Direction)}.
	 * @param blockEntity the block entity
	 * @return the energy storage
	 * @throws IllegalStateException if the block entity is not in any level
	 */
	public static EnergyStorage accessStorage(BlockEntity blockEntity) {
		return BlockEnergyImpl.accessStorage(blockEntity);
	}

	/**
	 * Defines an energy storage for this block.
	 * @param block the block
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @param handler the storage query handler
	 */
	public static void defineEnergyStorage(Block block, long capacity, EnergyUnit unit, StorageQueryHandler handler) {
		BlockEnergyImpl.defineEnergyStorage(block, capacity, unit, handler);
	}

	/**
	 * Defines only a query for the current block.
	 * <br>For example, it allows delegating the storage query to other block storages.
	 * @param block the block
	 * @param handler the storage query handler, without an internal storage
	 */
	public static void defineStorageQueryDelegate(Block block, StorageQueryDelegator handler) {
		BlockEnergyImpl.defineStorageQueryDelegate(block, handler);
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

	public interface StorageQueryDelegator {

		/**
		 * Handles a storage query with the provided context.
		 * @param level the level
		 * @param pos the block position
		 * @param side the side
		 * @return the possible energy storage
		 */
		@Nullable
		EnergyStorage handle(ServerLevel level, BlockPos pos, Direction side);
	}
}
