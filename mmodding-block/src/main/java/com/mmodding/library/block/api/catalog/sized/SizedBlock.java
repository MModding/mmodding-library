package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.function.consumer.TriConsumer;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * A {@link SizedBlock} is <b>one</b> block that expands on more than one block position.
 * <br>It uses {@link SizeManager} to define its size properties and handle them.
 * <br>
 * <br>It differs from a multiblock, which is a collection of blocks being placed relatively to each other
 * as a small structure.
 */
public abstract class SizedBlock extends Block {

	public SizedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getSizeManager().setInnerPos(this.defaultBlockState(), Vec3i.ZERO));
	}

	protected abstract SizeManager getSizeManager();

	protected Vec3i getPlacementOrigin(BlockState state) {
		return Vec3i.ZERO;
	}

	@Override
	@Nullable
	@MustBeInvokedByOverriders
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState defaultState = this.defaultBlockState();
		boolean isPlacementValid = true;
		BlockPos placementOrigin = context.getClickedPos().subtract(this.getPlacementOrigin(defaultState));
		for (int x = 0; x < this.getSizeManager().getLength(defaultState); x++) {
			for (int y = 0; y < this.getSizeManager().getHeight(defaultState); y++) {
				for (int z = 0; z < this.getSizeManager().getWidth(defaultState); z++) {
					BlockPos currentPos = placementOrigin.offset(x, y, z);
					isPlacementValid = isPlacementValid && context.getLevel().getBlockState(currentPos).canBeReplaced(context);
				}
			}
		}
		return isPlacementValid ? defaultState : null;
	}

	@Override
	@MustBeInvokedByOverriders
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (!level.isClientSide()) {
			this.forEach(level, pos.subtract(this.getPlacementOrigin(state)), state, (blockPos, _, innerPos) -> {
				level.setBlock(blockPos, this.getSizeManager().setInnerPos(state, innerPos), Block.UPDATE_ALL);
			});
			level.updateNeighborsAt(pos, Blocks.AIR);
			state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
		}
	}

	@Override
	@MustBeInvokedByOverriders
	public void spawnDestroyByEntityParticles(Level level, @Nullable Entity entity, BlockPos pos, BlockState state) {
		this.forEach(level, pos, state, (blockPos, blockState, _) -> {
			if (blockState.is(this)) super.spawnDestroyByEntityParticles(level, entity, blockPos, blockState);
		});
	}

	@Override
	@MustBeInvokedByOverriders
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		this.forEach(level, pos, state, (blockPos, blockState, _) -> {
			if (blockState.is(this)) level.removeBlock(blockPos, false);
		});
	}

	@Override
	protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
		Entity breaker = params.getOptionalParameter(LootContextParams.THIS_ENTITY);
		return breaker != null || this.getSizeManager().getInnerPos(state).equals(Vec3i.ZERO) ? super.getDrops(state, params) : List.of();
	}

	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return pos.offset(this.getSizeManager().getInnerPos(state).multiply(-1));
	}

	// iterates over the placed sized block
	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Vec3i> action) {
		BlockPos blockOrigin = this.getBlockOrigin(pos, state);
		for (int x = 0; x < this.getSizeManager().getLength(state); x++) {
			for (int y = 0; y < this.getSizeManager().getHeight(state); y++) {
				for (int z = 0; z < this.getSizeManager().getWidth(state); z++) {
					BlockPos currentPos = blockOrigin.offset(x, y, z);
					action.accept(currentPos, world.getBlockState(currentPos), new Vec3i(x, y, z));
				}
			}
		}
	}

	@Override
	@MustBeInvokedByOverriders
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		this.getSizeManager().defineSizeProperties(builder);
	}
}
