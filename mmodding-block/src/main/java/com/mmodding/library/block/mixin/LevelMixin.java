package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.block.api.event.LevelBlockEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public class LevelMixin {

	@WrapMethod(method = "setBlocksDirty")
	private void triggerDirtyChange(BlockPos pos, BlockState oldState, BlockState newState, Operation<Void> original) {
		original.call(pos, oldState, newState);
		LevelBlockEvents.DIRTY_CHANGE.invoker().onDirtyChange((Level) (Object) this, pos, oldState, newState);
	}
}
