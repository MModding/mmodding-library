package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.MModdingBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockState.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class BlockStateMixin extends AbstractBlockStateMixin implements MModdingBlockState {

	@Shadow
	protected abstract BlockState asBlockState();

	@Override
	public boolean canBeReplaced(@Nullable ItemPlacementContext context) {
		return this.getBlock().canBeReplaced(this.asBlockState(), context);
	}
}
