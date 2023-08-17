package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.tooltip.InventoryTooltipComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleTooltipComponent.class)
public class BundleTooltipComponentMixin {

	@Inject(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V", shift = At.Shift.AFTER), cancellable = true)
	private void drawSlot(int x, int y, int index, boolean shouldBlock, TextRenderer textRenderer, MatrixStack matrices, ItemRenderer itemRenderer, int z, CallbackInfo ci) {
		BundleTooltipComponent thisObject = (BundleTooltipComponent) (Object) this;
		if (thisObject instanceof InventoryTooltipComponent) {
			ci.cancel();
		}
	}

	@Inject(method = "getColumns", at = @At("HEAD"), cancellable = true)
	private void getColumns(CallbackInfoReturnable<Integer> cir) {
		BundleTooltipComponent thisObject = (BundleTooltipComponent) (Object) this;
		if (thisObject instanceof InventoryTooltipComponent component) {
			if (component.getData().getColumns().isPresent()) {
				cir.setReturnValue(component.getData().getColumns().getAsInt());
			}
		}
	}

	@Inject(method = "getRows", at = @At("HEAD"), cancellable = true)
	private void getRows(CallbackInfoReturnable<Integer> cir) {
		BundleTooltipComponent thisObject = (BundleTooltipComponent) (Object) this;
		if (thisObject instanceof InventoryTooltipComponent component) {
			if (component.getData().getRows().isPresent()) {
				cir.setReturnValue(component.getData().getRows().getAsInt());
			}
		}
	}
}
