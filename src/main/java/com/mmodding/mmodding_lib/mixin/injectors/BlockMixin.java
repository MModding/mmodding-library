package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin extends AbstractBlockMixin {

	@Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
	private void isTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (this.duckedSettings().getTranslucent()) {
			cir.setReturnValue(true);
		} else if (this.duckedSettings().getNotTranslucent()) {
			cir.setReturnValue(false);
		}
	}
}
