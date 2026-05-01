package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.math.api.AreaUtil;
import com.mmodding.library.math.api.OrientedBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
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

	public FacingSizedBlock(boolean horizontal, Properties settings) {
		this.horizontal = horizontal; // Needs to be set before createBlockStateDefinition getting called.
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(this.getFacingProperty(), Direction.NORTH));
	}

	@Override
	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return OrientedBlockPos.of(state.getValue(this.getFacingProperty()), Direction.UP, pos)
			.left(this.getInnerX(state))
			.bottom(this.getInnerY(state))
			.back(this.getInnerZ(state));
	}

	@Override
	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Vec3i> action) {
		System.out.println(pos);
		OrientedBlockPos blockOrigin = OrientedBlockPos.of(state.getValue(this.getFacingProperty()).getOpposite(), Direction.UP, this.getBlockOrigin(pos, state));
		for (int xStep = 0; xStep < this.getLength(); xStep++) {
			for (int yStep = 0; yStep < this.getHeight(); yStep++) {
				for (int zStep = 0; zStep < this.getWidth(); zStep++) {
					OrientedBlockPos currentPos = blockOrigin.right(xStep).top(yStep).front(zStep);
					action.accept(currentPos, world.getBlockState(currentPos), new Vec3i(xStep, yStep, zStep));
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
		return this.setInnerPos(state, AreaUtil.rotatePlacementInArea(this.getInnerPos(state), this.getLength(), this.getWidth(), rotation))
			.setValue(this.getFacingProperty(), rotation.rotate(state.getValue(this.getFacingProperty())));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(this.getFacingProperty())));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getFacingProperty());
	}

	public Property<Direction> getFacingProperty() {
		return this.horizontal ? BlockStateProperties.HORIZONTAL_FACING : BlockStateProperties.FACING;
	}
}
