package com.mmodding.library.rendering.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.impl.client.MModdingRenderingSetup;
import com.mmodding.library.rendering.impl.client.block.SpecialBlockRenderDispatcherImpl;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	@Final
	private LevelRenderState levelRenderState;

	@Shadow
	@Final
	private SubmitNodeStorage submitNodeStorage;

	@Inject(method = "submitFeatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;submitBlockEntities(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V"))
	private void addSpecialBlocksSubmission(LevelRenderState levelRenderState, SubmitNodeCollector submitNodeCollector, boolean renderOutline, CallbackInfo ci, @Local(name = "poseStack") PoseStack poseStack) {
		if (levelRenderState.getData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES) != null) {
			this.submitSpecialBlocks(poseStack, this.levelRenderState, this.submitNodeStorage);
			Objects.requireNonNull(levelRenderState.getData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES)).clear();
		}
	}

	// just like block entities, as usual, but without them
	@Unique
	private void submitSpecialBlocks(PoseStack poseStack, LevelRenderState levelRenderState, SubmitNodeCollector submitNodeCollector) {
		Vec3 cameraPos = levelRenderState.cameraRenderState.pos;
		double camX = cameraPos.x();
		double camY = cameraPos.y();
		double camZ = cameraPos.z();

		for (SpecialBlockRenderState renderState : Objects.requireNonNull(levelRenderState.getData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES))) {
			BlockPos blockPos = renderState.blockPos;
			poseStack.pushPose();
			poseStack.translate(blockPos.getX() - camX, blockPos.getY() - camY, blockPos.getZ() - camZ);
			SpecialBlockRenderDispatcherImpl.INSTANCE.submit(renderState, poseStack, submitNodeCollector, levelRenderState.cameraRenderState);
			poseStack.popPose();
		}
	}
}
