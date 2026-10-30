package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.math.api.AreaUtil;
import com.mmodding.library.math.api.OrientedBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

/**
 * {@link SizedBlock} variant supporting face rotations through {@link OrientedBlockPos}.
 */
public abstract class FacingSizedBlock extends SizedBlock {

	private final boolean horizontal;

	public FacingSizedBlock(boolean horizontal, Properties properties) {
		this.horizontal = horizontal; // Needs to be set before createBlockStateDefinition getting called.
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(this.getFacingProperty(), Direction.NORTH));
	}

	@Override
	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return super.getBlockOrigin(OrientedBlockPos.of(state.getValue(this.getFacingProperty()), Direction.UP, pos), state);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
		if (this.horizontal) {
			return super.getStateForPlacement(ctx).setValue(this.getFacingProperty(), ctx.getHorizontalDirection().getOpposite());
		}
		else {
			return super.getStateForPlacement(ctx).setValue(this.getFacingProperty(), ctx.getNearestLookingDirection().getOpposite());
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return this.getSizeManager().setInnerPos(state, AreaUtil.rotatePlacementInArea(this.getSizeManager().getInnerPos(state), this.getSizeManager().getLength(state), this.getSizeManager().getWidth(state), rotation))
			.setValue(this.getFacingProperty(), rotation.rotate(state.getValue(this.getFacingProperty())));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(this.getFacingProperty())));
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
