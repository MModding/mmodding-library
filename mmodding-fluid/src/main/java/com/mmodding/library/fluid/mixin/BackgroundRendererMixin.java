package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import com.mmodding.library.fluid.api.client.FluidClientRegistries;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.color.RGB;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class BackgroundRendererMixin {

	@Shadow
	private static float fogRed;

	@Shadow
	private static float fogGreen;

	@Shadow
	private static float fogBlue;

	@Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;fogBlue:F", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER, ordinal = 0))
	private static void changeWaterFogColor(Camera camera, float tickDelta, ClientLevel world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		BackgroundRendererMixin.applySpecialFog(world, camera);
	}

	@Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;fogBlue:F", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER, ordinal = 1))
	private static void changeLavaFogColor(Camera camera, float tickDelta, ClientLevel world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		BackgroundRendererMixin.applySpecialFog(world, camera);
	}

	@Unique
	private static void applySpecialFog(Level world, Camera camera) {
		Fluid fluid = world.getFluidState(camera.getBlockPosition()).getType();
		if (fluid instanceof FlowingFluid flowable) fluid = flowable.getSource();
		if (FluidClientRegistries.FOG_COLOR.contains(fluid)) {
			RGB rgb = Color.rgb(FluidClientRegistries.FOG_COLOR.get(fluid));
			BackgroundRendererMixin.fogRed = rgb.getRed() / 255.0f;
			BackgroundRendererMixin.fogGreen = rgb.getGreen() / 255.0f;
			BackgroundRendererMixin.fogBlue = rgb.getBlue() / 255.0f;
		}
	}
}
