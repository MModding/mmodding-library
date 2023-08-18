package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.tooltip.InventoryTooltipComponent;
import com.mmodding.mmodding_lib.library.utils.Self;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleTooltipComponent.class)
public class BundleTooltipComponentMixin implements Self<BundleTooltipComponent> {

	@Inject(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V", shift = At.Shift.AFTER), cancellable = true)
	private void drawSlot(int x, int y, int index, boolean shouldBlock, TextRenderer textRenderer, MatrixStack matrices, ItemRenderer itemRenderer, int z, CallbackInfo ci) {
		if (this.getObject() instanceof InventoryTooltipComponent) {
			ci.cancel();
		}
	}

	@Inject(method = "draw", at = @At("HEAD"), cancellable = true)
	private void draw(MatrixStack matrices, int x, int y, int z, BundleTooltipComponent.Sprite sprite, CallbackInfo ci) {
		if (this.getObject() instanceof InventoryTooltipComponent component) {
			if (component.getData().getTexture().isPresent()) {
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, component.getData().getTexture().get());
				DrawableHelper.drawTexture(matrices, x, y, z, sprite.u, sprite.v, sprite.width, sprite.height, 128, 128);
				ci.cancel();
			}
		}
	}

	@Inject(method = "getColumns", at = @At("HEAD"), cancellable = true)
	private void getColumns(CallbackInfoReturnable<Integer> cir) {
		if (this.getObject() instanceof InventoryTooltipComponent component) {
			if (component.getData().getColumns().isPresent()) {
				cir.setReturnValue(component.getData().getColumns().getAsInt());
			}
		}
	}

	@Inject(method = "getRows", at = @At("HEAD"), cancellable = true)
	private void getRows(CallbackInfoReturnable<Integer> cir) {
		if (this.getObject() instanceof InventoryTooltipComponent component) {
			if (component.getData().getRows().isPresent()) {
				cir.setReturnValue(component.getData().getRows().getAsInt());
			}
		}
	}
}
