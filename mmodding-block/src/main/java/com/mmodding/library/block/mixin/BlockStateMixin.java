package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.MModdingBlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockState.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class BlockStateMixin extends AbstractBlockStateMixin implements MModdingBlockState {

	@Shadow
	protected abstract BlockState asState();

	@Override
	public boolean canBeReplaced(@Nullable BlockPlaceContext context) {
		return this.getBlock().canBeReplaced(this.asState(), context);
	}
}
