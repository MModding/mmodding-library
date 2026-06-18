package com.mmodding.library.portal.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.portal.api.NodeBindingPortal;
import com.mmodding.library.portal.impl.storage.PortalNodeStorage;
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
			PortalNodeStorage storage = serverLevel.getServer().getDataStorage().get(PortalNodeStorage.TYPE);
			if (storage != null && oldState.getBlock() instanceof NodeBindingPortal portal && portal.persistent()) {
				storage.removeBoundFrom(serverLevel, pos);
			}
		}
	}
}
