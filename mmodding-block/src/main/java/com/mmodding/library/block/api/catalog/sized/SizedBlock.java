package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.container.Unit;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.state.api.property.ConstantIntProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class SizedBlock extends Block {

	final int xSize;
	final int ySize;
	final int zSize;

	protected abstract Property<Integer> getXProperty();

	protected abstract Property<Integer> getYProperty();

	protected abstract Property<Integer> getZProperty();

	public SizedBlock(int xSize, int ySize, int zSize, Properties settings) {
		super(settings);
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		Collection<Integer> xPropertyValues = this.getXProperty().getPossibleValues();
		int xMin = xPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int xMax = xPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (xMin != 0 || xMax != this.xSize - 1) {
			throw new IllegalStateException("Invalid x Coordinate of SizedBlock");
		}
		Collection<Integer> yPropertyValues = this.getYProperty().getPossibleValues();
		int yMin = yPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int yMax = yPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (yMin != 0 || yMax != this.ySize - 1) {
			throw new IllegalStateException("Invalid y Coordinate of SizedBlock");
		}
		Collection<Integer> zPropertyValues = this.getZProperty().getPossibleValues();
		int zMin = zPropertyValues.stream().min(Integer::compareTo).orElseThrow();
		int zMax = zPropertyValues.stream().max(Integer::compareTo).orElseThrow();
		if (zMin != 0 || zMax != this.zSize - 1) {
			throw new IllegalStateException("Invalid z Coordinate of SizedBlock");
		}
		this.registerDefaultState(this.defaultBlockState().setValue(this.getXProperty(), 0).setValue(this.getYProperty(), 0).setValue(this.getZProperty(), 0));
	}

	protected static Property<Integer> makeOfSize(SizeAxis axis, int limit_exclusive) {
		if (limit_exclusive == 1) {
			return ConstantIntProperty.of(axis.repr, 0);
		}
		else {
			return IntegerProperty.create(axis.repr, 0, limit_exclusive - 1);
		}
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Unit.Mutable<Boolean> canBeReplaced = Unit.mutable(true);
		this.forEach(
			ctx.getLevel(), ctx.getClickedPos(), ctx.getLevel().getBlockState(ctx.getClickedPos()),
			(pos, state, ijk) -> canBeReplaced.mutateValue(canBeReplaced.value() && state.canBeReplaced())
		);
		return canBeReplaced.value() ? super.getStateForPlacement(ctx) : null;
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
		Unit.Mutable<Boolean> canBeReplaced = Unit.mutable(true);
		this.forEach(
			world, blockPos, blockState,
			(pos, state, ijk) -> canBeReplaced.mutateValue(canBeReplaced.value() && state.canBeReplaced())
		);
		return canBeReplaced.value();
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(level, pos, state, entity, stack);
		if (!level.isClientSide()) {
			this.forEach(level, pos, state, (blockPos, blockState, ijk) -> level.setBlock(
				blockPos,
				state.setValue(this.getXProperty(), ijk.first()).setValue(this.getYProperty(), ijk.second()).setValue(this.getZProperty(), ijk.third()),
				Block.UPDATE_ALL
			));
			state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(world, pos, state, player);
		if (!world.isClientSide()) {
			this.forEach(world, pos, state, (blockPos, blockState, ijk) -> {
				if (!pos.equals(blockPos)) {
					world.setBlock(blockPos, blockState.getFluidState().createLegacyBlock(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
				}
			});
		}
		world.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getXProperty());
		builder.add(this.getYProperty());
		builder.add(this.getZProperty());
	}

	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return pos.offset(
			-state.getValue(this.getXProperty()),
			-state.getValue(this.getYProperty()),
			-state.getValue(this.getZProperty())
		);
	}

	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Triple<Integer, Integer, Integer>> action) {
		BlockPos blockOrigin = this.getBlockOrigin(pos, state);
		for (int i = 0; i < this.xSize; i++) {
			for (int j = 0; j < this.ySize; j++) {
				for (int k = 0; k < this.zSize; k++) {
					BlockPos currentPos = blockOrigin.offset(i, j, k);
					action.accept(currentPos, world.getBlockState(currentPos), Triple.create(i, j, k));
				}
			}
		}
	}

	public enum SizeAxis {
		LENGTH("x"),
		HEIGHT("y"),
		WIDTH("z");

		private final String repr;

		SizeAxis(String repr) {
			this.repr = repr;
		}
	}
}
