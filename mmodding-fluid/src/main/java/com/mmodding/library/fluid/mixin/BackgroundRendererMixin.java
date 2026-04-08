package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import com.mmodding.library.fluid.api.client.FluidClientRegistries;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.color.RGB;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
		BackgroundRendererMixin.applySpecialFog(world, camera);
	}

	@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BackgroundRenderer;blue:F", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER, ordinal = 1))
	private static void changeLavaFogColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		BackgroundRendererMixin.applySpecialFog(world, camera);
	}

	@Unique
	private static void applySpecialFog(World world, Camera camera) {
		Fluid fluid = world.getFluidState(camera.getBlockPos()).getFluid();
		if (fluid instanceof FlowableFluid flowable) fluid = flowable.getStill();
		if (FluidClientRegistries.FOG_COLOR.contains(fluid)) {
			RGB rgb = Color.rgb(FluidClientRegistries.FOG_COLOR.get(fluid));
			BackgroundRendererMixin.red = rgb.getRed() / 255.0f;
			BackgroundRendererMixin.green = rgb.getGreen() / 255.0f;
			BackgroundRendererMixin.blue = rgb.getBlue() / 255.0f;
		}
	}
}
