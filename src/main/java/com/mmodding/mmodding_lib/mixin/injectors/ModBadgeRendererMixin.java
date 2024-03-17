package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.integrations.modmenu.ModMenuIntegration;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.ModBadgeRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModBadgeRenderer.class, remap = false)
public abstract class ModBadgeRendererMixin {

	@Shadow
	public abstract void drawBadge(MatrixStack matrices, OrderedText text, int outlineColor, int fillColor, int mouseX, int mouseY);

	@Shadow
	public abstract Mod getMod();

	@Inject(method = "draw", at = @At(value = "INVOKE", target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
	private void draw(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci) {
		ModMenuIntegration.CUSTOM_BADGES_REGISTRY.forEach((identifier, badge) -> {
			if (badge.getMods().contains(this.getMod().getId())) {
				this.drawBadge(
					matrices,
					Text.translatable("badge." + identifier.getNamespace() + "." + identifier.getPath()).asOrderedText(),
					badge.getOutlineColor().toDecimal(),
					badge.getFillColor().toDecimal(),
					mouseX,
					mouseY
				);
			}
		});
	}
}
