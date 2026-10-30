package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.java.api.container.Unit;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SizedBlock extends Block {

	public SizedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getSizeManager().setInnerPos(this.defaultBlockState(), Vec3i.ZERO));
	}

	protected abstract SizeManager getSizeManager();

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
			this.forEach(level, pos, state, (blockPos, _, innerPos) -> {
				level.setBlock(blockPos, this.getSizeManager().setInnerPos(state, innerPos), Block.UPDATE_ALL);
			});
			level.updateNeighborsAt(pos, Blocks.AIR);
			state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(level, pos, state, player);
		if (!level.isClientSide()) {
			this.forEach(level, pos, state, (blockPos, blockState, _) -> {
				if (!pos.equals(blockPos)) {
					level.setBlock(blockPos, blockState.getFluidState().createLegacyBlock(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
					level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
				}
			});
		}
		return state;
	}

	public BlockPos getBlockOrigin(BlockPos pos, BlockState state) {
		return pos.offset(this.getSizeManager().getInnerPos(state).multiply(-1));
	}

	public void forEach(LevelReader world, BlockPos pos, BlockState state, TriConsumer<BlockPos, BlockState, Vec3i> action) {
		BlockPos blockOrigin = this.getBlockOrigin(pos, state);
		for (int x = 0; x < this.getSizeManager().getLength(state); x++) {
			for (int y = 0; y < this.getSizeManager().getHeight(state); y++) {
				for (int z = 0; z < this.getSizeManager().getWidth(state); z++) {
					BlockPos currentPos = blockOrigin.offset(x, y, z);
					action.accept(currentPos, world.getBlockState(currentPos), new Vec3i(x, y, z));
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		this.getSizeManager().defineSizeProperties(builder);
	}
}
