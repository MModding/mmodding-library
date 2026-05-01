package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.container.Unit;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public abstract class SizedBlock extends Block {

	protected abstract BlockState setInnerX(BlockState state, int x);

	protected abstract BlockState setInnerY(BlockState state, int y);

	protected abstract BlockState setInnerZ(BlockState state, int z);

	protected abstract int getInnerX(BlockState state);

	protected abstract int getInnerY(BlockState state);

	protected abstract int getInnerZ(BlockState state);

	protected abstract int getLength();

	protected abstract int getHeight();

	protected abstract int getWidth();

	public SizedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.setInnerPos(this.defaultBlockState(), Vec3i.ZERO));
	}

	protected static IntegerProperty makeOfSize(SizeAxis axis, int limit_exclusive) {
		return IntegerProperty.create(axis.repr, 0, limit_exclusive - 1);
	}

	protected BlockState setInnerPos(BlockState state, Vec3i pos) {
		return this.setInnerZ(this.setInnerY(this.setInnerX(state, pos.getX()), pos.getY()), pos.getZ());
	}

	protected Vec3i getInnerPos(BlockState state) {
		return new Vec3i(this.getInnerX(state), this.getInnerY(state), this.getInnerZ(state));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState candidate = super.getStateForPlacement(ctx);
		Unit.Mutable<Boolean> noBlockCollisions = Unit.mutable(true);
		this.forEach(
			ctx.getLevel(), ctx.getClickedPos(), candidate,
			(_, state, _) -> noBlockCollisions.mutateValue(noBlockCollisions.value() && state.canBeReplaced())
		);
		if (noBlockCollisions.value()) {
			return candidate;
		}
		else {
			return null;
		}
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
		Unit.Mutable<Boolean> canBeReplaced = Unit.mutable(true);
		this.forEach(
			world, blockPos, blockState,
			(_, state, _) -> canBeReplaced.mutateValue(canBeReplaced.value() && state.canBeReplaced())
		);
		return canBeReplaced.value();
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(level, pos, state, entity, stack);
		if (!level.isClientSide()) {
			this.forEach(level, pos, state, (blockPos, _, innerPos) ->
				level.setBlock(blockPos, this.setInnerPos(state, innerPos), Block.UPDATE_ALL));
			state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(world, pos, state, player);
		if (!world.isClientSide()) {
			this.forEach(world, pos, state, (blockPos, blockState, _) -> {
				if (!pos.equals(blockPos)) {
					world.setBlock(blockPos, blockState.getFluidState().createLegacyBlock(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
				}
			});
		}
		world.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		return state;
	}

	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return pos.offset(-this.getInnerX(state), -this.getInnerY(state), -this.getInnerZ(state));
	}

	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Vec3i> action) {
		BlockPos blockOrigin = this.getBlockOrigin(pos, state);
		for (int x = 0; x < this.getLength(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				for (int z = 0; z < this.getWidth(); z++) {
					BlockPos currentPos = blockOrigin.offset(x, y, z);
					action.accept(currentPos, world.getBlockState(currentPos), new Vec3i(x, y, z));
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
