package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.function.consumer.TriConsumer;
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
		super(settings);
		this.horizontal = horizontal;
		this.registerDefaultState(this.defaultBlockState().setValue(this.getFacingProperty(), Direction.NORTH));
	}

	@Override
	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Vec3i> action) {
		OrientedBlockPos blockOrigin = OrientedBlockPos.of(state.getValue(this.getFacingProperty()), this.getBlockOrigin(pos, state));
		for (int x = 0; x < this.getLength(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				for (int z = 0; z < this.getWidth(); z++) {
					OrientedBlockPos currentPos = blockOrigin.front(x).top(y).left(z);
					action.accept(currentPos, world.getBlockState(currentPos), new Vec3i(x, y, z));
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
		Vec3i prevInnerPos = this.getInnerPos(state);
		BlockState rotated = this.setInnerPos(state, new Vec3i(
			this.getWidth() - prevInnerPos.getZ(),
			this.getHeight(),
			this.getLength() - prevInnerPos.getX()
		));
		return rotated.setValue(this.getFacingProperty(), rotation.rotate(state.getValue(this.getFacingProperty())));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		Vec3i prevInnerPos = this.getInnerPos(state);
		BlockState mirrored = this.setInnerPos(state, new Vec3i(
			this.getLength() - prevInnerPos.getX(),
			this.getHeight(),
			this.getWidth() - prevInnerPos.getZ()
		));
		return mirrored.rotate(mirror.getRotation(state.getValue(this.getFacingProperty())));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getFacingProperty());
	}

	public Property<Direction> getFacingProperty() {
		return this.horizontal ? BlockStateProperties.HORIZONTAL_FACING : BlockStateProperties.FACING;
	}
}
