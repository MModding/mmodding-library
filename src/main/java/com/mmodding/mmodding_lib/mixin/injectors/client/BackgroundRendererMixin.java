package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

	@Shadow
	private static float red;

	@Shadow
	private static float green;

	@Shadow
	private static float blue;

	@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BackgroundRenderer;blue:F", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER, ordinal = 0))
	private static void changeWaterFogColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		if (world.getFluidState(camera.getBlockPos()).getFluid() instanceof FluidExtensions extensions) {
			BackgroundRendererMixin.red = extensions.getFogColor().getRed() / 255.0f;
			BackgroundRendererMixin.green = extensions.getFogColor().getGreen() / 255.0f;
			BackgroundRendererMixin.blue = extensions.getFogColor().getBlue() / 255.0f;
		}
	}

	@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BackgroundRenderer;blue:F", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER, ordinal = 1))
	private static void changeLavaFogColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		if (world.getFluidState(camera.getBlockPos()).getFluid() instanceof FluidExtensions extensions) {
			BackgroundRendererMixin.red = extensions.getFogColor().getRed() / 255.0f;
			BackgroundRendererMixin.green = extensions.getFogColor().getGreen() / 255.0f;
			BackgroundRendererMixin.blue = extensions.getFogColor().getBlue() / 255.0f;
		}
	}
}
