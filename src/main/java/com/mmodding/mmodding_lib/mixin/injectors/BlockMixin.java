package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.base.api.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin extends AbstractBlockMixin {

    @Shadow
	public BlockState getDefaultState() {
		throw new AssertionError();
	}

	@Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
	private void isTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (this.duckedSettings().mmodding_lib$getTranslucent() == TriState.TRUE) {
			cir.setReturnValue(true);
		} else if (this.duckedSettings().mmodding_lib$getTranslucent() == TriState.FALSE) {
			cir.setReturnValue(false);
		}
	}
}
