package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, priority = 3000)
public abstract class WorldRendererMixin {

    @Nullable
    @Shadow
    private ClientWorld world;

    @Shadow
    protected abstract boolean method_43788(Camera camera);

    @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
    private void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera preStep, boolean bl, Runnable runnable, CallbackInfo info) {
        this.renderStellarObjects(matrices, tickDelta, preStep, bl);
    }

    @Unique
    private void renderStellarObjects(MatrixStack matrices, float tickDelta, Camera preStep, boolean bl) {
        if (this.world != null && !bl) {
            CameraSubmersionType cameraSubmersionType = preStep.getSubmersionType();
            if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && !this.method_43788(preStep)) {
                MModdingClientGlobalMaps.getStellarObjects().forEachOrdered(stellarObject -> {
					if (stellarObject.getCycle().getWorldKey().equals(this.world.getRegistryKey())) {
						stellarObject.render(matrices, this.world, tickDelta);
					}
				});
            }
        }
    }
}
