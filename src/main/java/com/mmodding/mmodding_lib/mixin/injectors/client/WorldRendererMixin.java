package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import net.minecraft.client.render.*;
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

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", shift = At.Shift.AFTER))
    private void renderSky(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        this.renderStellarObjects(matrices, tickDelta, camera);
    }

    @Unique
    private void renderStellarObjects(MatrixStack matrices, float tickDelta, Camera camera) {
        if (this.world != null) {
            CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
            if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && !this.method_43788(camera)) {
                MModdingClientGlobalMaps.getStellarObjects().forEachOrdered(stellarObject -> {
					if (stellarObject.getCycle().getWorldKey().equals(this.world.getRegistryKey())) {
						stellarObject.render(matrices, this.world, tickDelta);
					}
				});
            }
        }
    }
}
