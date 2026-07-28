package com.mmodding.library.energy.api.block;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.impl.block.BlockEnergyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;

public final class BlockEnergy {

	private BlockEnergy() {}

	/**
	 * Queries an {@link EnergyAccess} in a specified level, at a specified block position, and for a specified side.
	 * @param level the level
	 * @param pos the block position
	 * @param side the side
	 * @return the possibly found energy access
	 */
	@Nullable
	public static EnergyAccess query(ServerLevel level, BlockPos pos, @Nullable Direction side) {
		return BlockEnergyImpl.query(level, pos, side);
	}

	/**
	 * Accesses the {@link EnergyComponent} associated to the given {@link BlockEntity}.
	 * <br>You can see a block entity as a block "instance"; the purpose of this method is, by such, to give access
	 * to the component associated to the block entity, only when defining its behavior through its implementation.
	 * To look up and interact with other blocks you will <b>always use</b>
	 * {@link BlockEnergy#query(ServerLevel, BlockPos, Direction)}.
	 * @param blockEntity the block entity
	 * @return the energy component
	 * @throws IllegalStateException if the block entity is not in any level or if it is client-sided
	 */
	public static EnergyComponent retrieveFrom(BlockEntity blockEntity) {
		return BlockEnergyImpl.retrieveFrom(blockEntity);
	}

	/**
	 * Defines the energy specification for this block.
	 * @param block the block
	 * @param capacity the energy capacity
	 * @param unit the energy unit
	 * @param handler the access query handler
	 * @throws IllegalStateException if the block already has a query handler definition
	 */
	public static void defineEnergy(Block block, long capacity, EnergyUnit unit, AccessQueryHandler handler) {
		BlockEnergy.defineEnergy(block, (_, _) -> capacity, unit, handler);
	}

	/**
	 * Defines the energy specification for this block.
	 * @param block the block
	 * @param capacityGetter the energy capacity getter
	 * @param unit the energy unit
	 * @param handler the access query handler
	 * @throws IllegalStateException if the block already has a query handler definition
	 */
	public static <T extends BlockEntity> void defineEnergy(Block block, BiFunction<BlockState, @Nullable T, Long> capacityGetter, EnergyUnit unit, AccessQueryHandler handler) {
		BlockEnergyImpl.defineEnergy(block, capacityGetter, unit, handler);
	}

	/**
	 * Defines only a query for the current block.
	 * <br>For example, it allows delegating the access query to other block accesses.
	 * @param block the block
	 * @param handler the headless query handler
	 * @throws IllegalStateException if the block already has a query handler definition
	 */
	public static void defineEnergyDelegate(Block block, HeadlessQueryHandler handler) {
		BlockEnergyImpl.defineEnergyDelegate(block, handler);
	}

	public interface AccessQueryHandler {

		/**
		 * Handles an access query with provided context.
		 * @param level the level
		 * @param pos the block position
		 * @param state the block state
		 * @param blockEntity the possibly existing block entity
		 * @param side the side
		 * @param internalComponent the internal component for the block instance
		 * @return the possible energy access
		 */
		@Nullable
		EnergyAccess handle(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Direction side, EnergyComponent internalComponent);
	}

	public interface HeadlessQueryHandler {

		/**
		 * Handles an access query with the provided context.
		 * @param level the level
		 * @param pos the block position
		 * @param state the block state
		 * @param blockEntity the possibly existing block entity
		 * @param side the side
		 * @return the possible energy access
		 */
		@Nullable
		EnergyAccess handle(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Direction side);
	}
}
