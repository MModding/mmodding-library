package com.mmodding.library.block.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@InjectedContent(BlockBehaviour.class)
public interface MModdingBlock {

	@ApiStatus.NonExtendable
	default boolean canBeReplaced(BlockState state) {
		return this.canBeReplaced(state, null);
	}

	/**
	 * Better version of {@link Block#canBeReplaced(BlockState, BlockPlaceContext)} that can be used without the {@link BlockPlaceContext}.
	 * @param state the {@link BlockState}
	 * @param context the optional {@link BlockPlaceContext}
	 * @return a boolean indicating if the block accepts replacement
	 * @apiNote this method only exists if you want to implement the method specifically in the case of {@link BlockPlaceContext} being null.
	 * Do not forget to make the super-call when that is not the case!
	 */
	@ApiStatus.OverrideOnly
	default boolean canBeReplaced(BlockState state, @Nullable BlockPlaceContext context) {
		throw new IllegalStateException();
	}

	default float getVelocityMultiplier(Level world, BlockPos pos, BlockState state) {
		return 1.0f;
	}
}
