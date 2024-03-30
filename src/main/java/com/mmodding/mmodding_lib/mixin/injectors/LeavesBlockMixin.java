package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.blocks.CustomLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LeavesBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
	private void removeConditionally(LeavesBlock instance, BlockState blockState, Operation<Void> original) {
		if (!(instance instanceof CustomLeavesBlock)) {
			original.call(instance, blockState);
		}
	}
}
