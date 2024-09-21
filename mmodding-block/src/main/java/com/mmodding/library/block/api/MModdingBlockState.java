package com.mmodding.library.block.api;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface MModdingBlockState {

	@ApiStatus.NonExtendable
	default boolean canBeReplaced() {
		return this.canBeReplaced(null);
	}

	/**
	 * Better version of {@link BlockState#canReplace(ItemPlacementContext)} that can be used without the {@link ItemPlacementContext}.
	 * @param context the optional {@link ItemPlacementContext}
	 * @return a boolean indicating if this {@link BlockState} can be replaced
	 */
	default boolean canBeReplaced(@Nullable ItemPlacementContext context) {
		throw new IllegalStateException();
	}
}
