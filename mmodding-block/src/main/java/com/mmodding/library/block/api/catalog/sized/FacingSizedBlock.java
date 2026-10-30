package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.math.api.AreaUtil;
import com.mmodding.library.math.api.OrientedBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jspecify.annotations.Nullable;

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
	@Nullable
	@MustBeInvokedByOverriders
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = this.horizontal ? context.getHorizontalDirection().getOpposite() : context.getNearestLookingDirection().getOpposite();
		BlockState defaultState = this.defaultBlockState().setValue(this.getFacingProperty(), facing);
		boolean isPlacementValid = true;
		OrientedBlockPos placementOrigin = (OrientedBlockPos) OrientedBlockPos.of(facing, Direction.UP, context.getClickedPos()).subtract(this.getPlacementOrigin(defaultState));
		for (int x = 0; x < this.getSizeManager().getLength(defaultState); x++) {
			for (int y = 0; y < this.getSizeManager().getHeight(defaultState); y++) {
				for (int z = 0; z < this.getSizeManager().getWidth(defaultState); z++) {
					OrientedBlockPos currentPos = placementOrigin.offset(x, y, z);
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
			this.forEach(level, OrientedBlockPos.of(state.getValue(this.getFacingProperty()), Direction.UP, pos).subtract(this.getPlacementOrigin(state)), state, (blockPos, _, innerPos) -> {
				level.setBlock(blockPos, this.getSizeManager().setInnerPos(state, innerPos), Block.UPDATE_ALL);
			});
			level.updateNeighborsAt(pos, Blocks.AIR);
			state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
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
