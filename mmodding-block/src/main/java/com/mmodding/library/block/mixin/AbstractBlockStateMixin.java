package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

	@Shadow
	public abstract Block getBlock();

	@Inject(method = "hasBlockEntity", at = @At("HEAD"), cancellable = true)
	private void injectCustomBedBlockBehavior(CallbackInfoReturnable<Boolean> cir) {
		if (this.getBlock() instanceof SimpleBedBlock) {
			cir.setReturnValue(false);
		}
	}
}
