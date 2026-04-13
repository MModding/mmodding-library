package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.math.api.OrientedBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * {@link SizedBlock} variant supporting face rotations through {@link OrientedBlockPos}.
 */
public abstract class FacingSizedBlock extends SizedBlock {

	private final boolean horizontal;

	public FacingSizedBlock(int xSize, int ySize, int zSize, boolean horizontal, Properties settings) {
		super(xSize, ySize, zSize, settings);
		this.registerDefaultState(this.defaultBlockState().setValue(this.getFacingProperty(), Direction.NORTH));
		this.horizontal = horizontal;
	}

	@Override
	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Triple<Integer, Integer, Integer>> action) {
		OrientedBlockPos blockOrigin = OrientedBlockPos.of(state.getValue(this.getFacingProperty()), this.getBlockOrigin(pos, state));
		for (int i = 0; i < this.xSize; i++) {
			for (int j = 0; j < this.ySize; j++) {
				for (int k = 0; k < this.zSize; k++) {
					OrientedBlockPos currentPos = blockOrigin.front(i).top(j).left(k);
					action.accept(currentPos, world.getBlockState(currentPos), Triple.create(i, j, k));
				}
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(this.getFacingProperty(), this.horizontal ? ctx.getHorizontalDirection().getOpposite() : ctx.getNearestLookingDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		int prevX = state.getValue(this.getXProperty());
		int prevY = state.getValue(this.getYProperty());
		int prevZ = state.getValue(this.getZProperty());
		BlockState rotated = state
			.setValue(this.getXProperty(), this.zSize - prevZ)
			.setValue(this.getYProperty(), prevY)
			.setValue(this.getZProperty(), this.xSize - prevX);
		return rotated.setValue(this.getFacingProperty(), rotation.rotate(state.getValue(this.getFacingProperty())));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		int prevX = state.getValue(this.getXProperty());
		int prevY = state.getValue(this.getYProperty());
		int prevZ = state.getValue(this.getZProperty());
		BlockState mirrored = state
			.setValue(this.getXProperty(), this.xSize - prevX)
			.setValue(this.getYProperty(), prevY)
			.setValue(this.getZProperty(), this.zSize - prevZ);
		return mirrored.rotate(mirror.getRotation(state.getValue(this.getFacingProperty())));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(this.getFacingProperty());
	}

	public Property<Direction> getFacingProperty() {
		return this.horizontal ? BlockStateProperties.HORIZONTAL_FACING : BlockStateProperties.FACING;
	}
}
