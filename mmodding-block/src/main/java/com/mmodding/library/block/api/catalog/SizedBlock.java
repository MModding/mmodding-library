package com.mmodding.library.block.api.catalog;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.container.Unit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class SizedBlock extends Block {

	private final int xSize;
	private final int ySize;
	private final int zSize;

	protected abstract IntProperty getXProperty();

	protected abstract IntProperty getYProperty();

	protected abstract IntProperty getZProperty();

	public SizedBlock(int xSize, int ySize, int zSize, Settings settings) {
		super(settings);
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		Collection<Integer> xPropertyValues = this.getXProperty().getValues();
		int xMin = xPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int xMax = xPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (xMin != 0 || xMax != this.xSize - 1) {
			throw new IllegalStateException("Invalid x Coordinate of SizedBlock");
		}
		Collection<Integer> yPropertyValues = this.getYProperty().getValues();
		int yMin = yPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int yMax = yPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (yMin != 0 || yMax != this.ySize - 1) {
			throw new IllegalStateException("Invalid y Coordinate of SizedBlock");
		}
		Collection<Integer> zPropertyValues = this.getZProperty().getValues();
		int zMin = zPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int zMax = zPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (zMin != 0 || zMax != this.zSize - 1) {
			throw new IllegalStateException("Invalid z Coordinate of SizedBlock");
		}
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		Unit.Mutable<Boolean> canBeReplaced = Unit.mutable(true);
		this.forEach(
			ctx.getWorld(), ctx.getBlockPos(), ctx.getWorld().getBlockState(ctx.getBlockPos()),
			(pos, state, ijk) -> canBeReplaced.mutateValue(canBeReplaced.value() && state.canBeReplaced())
		);
		return canBeReplaced.value() ? super.getPlacementState(ctx) : null;
	}

	@Override
	public boolean canPlaceAt(BlockState blockState, WorldView world, BlockPos blockPos) {
		Unit.Mutable<Boolean> canBeReplaced = Unit.mutable(true);
		this.forEach(
			world, blockPos, blockState,
			(pos, state, ijk) -> canBeReplaced.mutateValue(canBeReplaced.value() && state.canBeReplaced())
		);
		return canBeReplaced.value();
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.onPlaced(world, pos, state, entity, stack);
		if (!world.isClient()) {
			this.forEach(world, pos, state, (blockPos, blockState, ijk) -> world.setBlockState(
				blockPos,
				state.with(this.getXProperty(), ijk.first()).with(this.getYProperty(), ijk.second()).with(this.getZProperty(), ijk.third()),
				Block.NOTIFY_ALL
			));
			world.updateNeighbors(pos, Blocks.AIR);
			state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		if (!world.isClient()) {
			this.forEach(world, pos, state, (blockPos, blockState, ijk) -> {
				if (!pos.equals(blockPos)) {
					world.setBlockState(blockPos, blockState.getFluidState().getBlockState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
				}
			});
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(this.getXProperty());
		builder.add(this.getYProperty());
		builder.add(this.getZProperty());
	}

	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return pos.add(
			-state.get(this.getXProperty()),
			-state.get(this.getYProperty()),
			-state.get(this.getZProperty())
		);
	}

	public void forEach(WorldView world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Triple<Integer, Integer, Integer>> action) {
		BlockPos blockOrigin = this.getBlockOrigin(pos, state);
		for (int i = 0; i < this.xSize; i++) {
			for (int j = 0; j < this.ySize; j++) {
				for (int k = 0; k < this.zSize; k++) {
					BlockPos currentPos = blockOrigin.add(i, j, k);
					action.accept(currentPos, world.getBlockState(currentPos), Triple.create(i, j, k));
				}
			}
		}
	}
}
