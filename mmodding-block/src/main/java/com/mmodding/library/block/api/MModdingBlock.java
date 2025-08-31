package com.mmodding.library.block.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;

@InjectedContent(AbstractBlock.class)
public interface MModdingBlock {

	@ApiStatus.NonExtendable
	default boolean canBeReplaced(BlockState state) {
		return this.canBeReplaced(state, null);
	}

	/**
	 * Better version of {@link Block#canReplace(BlockState, ItemPlacementContext)} that can be used without the {@link ItemPlacementContext}.
	 * @param state the {@link BlockState}
	 * @param context the optional {@link ItemPlacementContext}
	 * @return a boolean indicating if the block accepts replacement
	 * @apiNote this method only exists if you want to implement the method specifically in the case of {@link ItemPlacementContext} being null.
	 * Do not forget to make the super-call when that is not the case!
	 */
	@ApiStatus.OverrideOnly
	@MustBeInvokedByOverriders
	default boolean canBeReplaced(BlockState state, @Nullable ItemPlacementContext context) {
		throw new IllegalStateException();
	}
}
