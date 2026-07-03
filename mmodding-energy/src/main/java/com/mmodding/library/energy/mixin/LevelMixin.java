package com.mmodding.library.energy.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.energy.impl.block.BlockEnergySavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public class LevelMixin {

	@WrapMethod(method = "setBlocksDirty")
	private void triggerDirtyChange(BlockPos pos, BlockState oldState, BlockState newState, Operation<Void> original) {
		original.call(pos, oldState, newState);
		if ((Object) this instanceof ServerLevel serverLevel) {
			BlockEnergySavedData storage = serverLevel.getDataStorage().get(BlockEnergySavedData.TYPE);
			if (storage != null) {
				if (oldState.getBlock() == newState.getBlock()) {
					storage.updateState(pos, newState);
				}
				else {
					storage.removeIfPresent(pos);
				}
			}
		}
	}
}
