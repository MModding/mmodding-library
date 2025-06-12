package com.mmodding.library.block.api.catalog;

import com.mmodding.library.java.api.function.SingleTypeFunction;
import com.mmodding.library.java.api.object.Opposable;
import com.mmodding.library.math.api.RelativeBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class DoubleWidthBlock extends Block {

	private static final EnumProperty<DoubleWidthPart> PART = EnumProperty.of("part", DoubleWidthPart.class);
	private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public DoubleWidthBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(DoubleWidthBlock.PART, DoubleWidthPart.ORIGIN).with(FACING, Direction.NORTH));
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		RelativeBlockPos relative = RelativeBlockPos.of(ctx.getBlockPos()).apply(ctx.getHorizontalPlayerFacing());
		boolean validOrigin = ctx.getWorld().getBlockState(relative).canBeReplaced(ctx);
		boolean validSub0 = ctx.getWorld().getBlockState(relative.front()).canBeReplaced(ctx);
		boolean validSub1 = ctx.getWorld().getBlockState(relative.front().left()).canBeReplaced(ctx);
		boolean validSub2 = ctx.getWorld().getBlockState(relative.left()).canBeReplaced(ctx);
		return validOrigin && validSub0 && validSub1 && validSub2 ? this.getDefaultState().with(PART, DoubleWidthPart.ORIGIN).with(FACING, ctx.getHorizontalPlayerFacing()) : null;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		RelativeBlockPos origin = state.get(PART).toOrigin(pos, state.get(FACING));
		BlockPos originPos = new BlockPos(origin);
		BlockPos sub0Pos = new BlockPos(origin.front());
		BlockPos sub1Pos = new BlockPos(origin.front().left());
		BlockPos sub2Pos = new BlockPos(origin.left());
		boolean validOrigin = world.getBlockState(originPos).canBeReplaced();
		boolean validSub0 = world.getBlockState(sub0Pos).canBeReplaced();
		boolean validSub1 = world.getBlockState(sub1Pos).canBeReplaced();
		boolean validSub2 = world.getBlockState(sub2Pos).canBeReplaced();
		return validOrigin && validSub0 && validSub1 && validSub2;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (!world.isClient()) {
			RelativeBlockPos relative = RelativeBlockPos.of(pos).apply(state.get(FACING));
			world.setBlockState(relative.front(), state.with(PART, DoubleWidthPart.SUB_PART_0), Block.NOTIFY_ALL);
			world.setBlockState(relative.front().left(), state.with(PART, DoubleWidthPart.SUB_PART_1), Block.NOTIFY_ALL);
			world.setBlockState(relative.left(), state.with(PART, DoubleWidthPart.SUB_PART_2), Block.NOTIFY_ALL);
			world.updateNeighbors(pos, Blocks.AIR);
			state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		if (!world.isClient()) {
			RelativeBlockPos origin = state.get(PART).toOrigin(pos, state.get(FACING));
			if (!origin.equals(pos)) {
				world.setBlockState(origin, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			}
			if (!origin.front().equals(pos)) {
				world.setBlockState(origin.front(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			}
			if (!origin.front().left().equals(pos)) {
				world.setBlockState(origin.front().left(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			}
			if (!origin.left().equals(pos)) {
				world.setBlockState(origin.left(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			}
			world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
		}
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.cycle(PART).with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return mirror == BlockMirror.NONE ? state : state.with(PART, state.get(PART).getOpposite()).rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PART);
		builder.add(FACING);
	}

	public enum DoubleWidthPart implements Opposable<DoubleWidthPart>, StringIdentifiable {
		ORIGIN(relative -> relative),
		SUB_PART_0(RelativeBlockPos::behind),
		SUB_PART_1(relative -> relative.behind().right()),
		SUB_PART_2(RelativeBlockPos::right);

		private final SingleTypeFunction<RelativeBlockPos> mutator;

		DoubleWidthPart(SingleTypeFunction<RelativeBlockPos> mutator) {
			this.mutator = mutator;
		}

		public RelativeBlockPos toOrigin(BlockPos pos, Direction direction) {
			return this.mutator.apply(RelativeBlockPos.of(pos).apply(direction));
		}

		@Override
		public DoubleWidthPart getOpposite() {
			return switch (this) {
				case ORIGIN -> SUB_PART_1;
				case SUB_PART_0 -> SUB_PART_2;
				case SUB_PART_1 -> ORIGIN;
				case SUB_PART_2 -> SUB_PART_0;
			};
		}

		@Override
		public String asString() {
			return switch (this) {
				case ORIGIN -> "origin";
				case SUB_PART_0 -> "sub_part_0";
				case SUB_PART_1 -> "sub_part_1";
				case SUB_PART_2 -> "sub_part_2";
			};
		}
	}
}
