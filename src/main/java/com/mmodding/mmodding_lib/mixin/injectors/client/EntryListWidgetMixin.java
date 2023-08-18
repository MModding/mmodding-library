package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.MModdingModConfigsScreen;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigElementsListWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntryListWidget.class)
public class EntryListWidgetMixin {

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", shift = At.Shift.AFTER))
	private void changeBackgroundTexture(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (((EntryListWidget<?>) (Object) this) instanceof MModdingModConfigsScreen.MModdingModConfigsListWidget mmoddingModConfigsList) {
			RenderSystem.setShaderTexture(0, MModdingLib.LIBRARY_CONFIG.getConfigOptions().textureLocation());
		}
		if (((EntryListWidget<?>) (Object) this) instanceof ConfigElementsListWidget configElementsList) {
			RenderSystem.setShaderTexture(0, configElementsList.getConfig().getConfigOptions().textureLocation());
		}
	}
}
