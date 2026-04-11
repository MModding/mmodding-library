package com.mmodding.library.block.api;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface MModdingBlockState {

	@ApiStatus.NonExtendable
	default boolean canBeReplaced() {
		return this.canBeReplaced(null);
	}

	/**
	 * Better version of {@link BlockState#canBeReplaced(BlockPlaceContext)} that can be used without the {@link BlockPlaceContext}.
	 * @param context the optional {@link BlockPlaceContext}
	 * @return a boolean indicating if this {@link BlockState} can be replaced
	 */
	default boolean canBeReplaced(@Nullable BlockPlaceContext context) {
		throw new IllegalStateException();
	}
}
