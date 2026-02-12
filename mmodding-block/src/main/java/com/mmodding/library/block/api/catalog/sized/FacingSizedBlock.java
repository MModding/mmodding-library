package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.math.api.OrientedBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.util.TriConsumer;

/**
 * {@link SizedBlock} variant supporting face rotations through {@link OrientedBlockPos}.
 */
public abstract class FacingSizedBlock extends SizedBlock {

	private final boolean horizontal;

	public FacingSizedBlock(int xSize, int ySize, int zSize, boolean horizontal, Settings settings) {
		super(xSize, ySize, zSize, settings);
		this.setDefaultState(this.getDefaultState().with(this.getFacingProperty(), Direction.NORTH));
		this.horizontal = horizontal;
	}

	@Override
	public void forEach(WorldView world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Triple<Integer, Integer, Integer>> action) {
		OrientedBlockPos blockOrigin = OrientedBlockPos.of(state.get(this.getFacingProperty()), this.getBlockOrigin(pos, state));
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
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(this.getFacingProperty(), this.horizontal ? ctx.getHorizontalPlayerFacing().getOpposite() : ctx.getPlayerLookDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		int prevX = state.get(this.getXProperty());
		int prevY = state.get(this.getYProperty());
		int prevZ = state.get(this.getZProperty());
		BlockState rotated = state
			.with(this.getXProperty(), this.zSize - prevZ)
			.with(this.getYProperty(), prevY)
			.with(this.getZProperty(), this.xSize - prevX);
		return rotated.with(this.getFacingProperty(), rotation.rotate(state.get(this.getFacingProperty())));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		int prevX = state.get(this.getXProperty());
		int prevY = state.get(this.getYProperty());
		int prevZ = state.get(this.getZProperty());
		BlockState mirrored = state
			.with(this.getXProperty(), this.xSize - prevX)
			.with(this.getYProperty(), prevY)
			.with(this.getZProperty(), this.zSize - prevZ);
		return mirrored.rotate(mirror.getRotation(state.get(this.getFacingProperty())));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(this.getFacingProperty());
	}

	public DirectionProperty getFacingProperty() {
		return this.horizontal ? Properties.HORIZONTAL_FACING : Properties.FACING;
	}
}
