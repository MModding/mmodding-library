package com.mmodding.library.rendering.mixin;

import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.impl.client.CompiledSectionMeshDuck;
import com.mmodding.library.rendering.impl.client.MModdingRenderingSetup;
import com.mmodding.library.rendering.impl.client.block.SpecialBlockRenderDispatcherImpl;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.SortedSet;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	@Final
	private LevelRenderer levelRenderer;

	@Shadow
	private @Nullable ClientLevel level;

	@Shadow
	@Final
	private LevelRenderState levelRenderState;

	@Inject(method = "extract", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;blockEntityRenderDispatcher()Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;"))
	private void prepareCamera(DeltaTracker deltaTracker, Camera camera, float worldPartialTicks, CallbackInfo ci) {
		SpecialBlockRenderDispatcherImpl.INSTANCE.prepare(camera.position());
	}

	@Inject(method = "extract", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/extract/LevelExtractor;extractVisibleBlockEntities(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/state/level/LevelRenderState;)V"))
	private void addSpecialBlocksExtraction(DeltaTracker deltaTracker, Camera camera, float worldPartialTicks, CallbackInfo ci) {
		Profiler.get().popPush("specialBlocks");
		this.extractVisibleSpecialBlocks(camera, worldPartialTicks, this.levelRenderState);
	}

	// replication of extractVisibleBlockEntities, without manipulating block entities, but instead a pair of (block pos, block state)
	// render states are then used in the level renderer
	@Unique
	private void extractVisibleSpecialBlocks(final Camera camera, final float deltaPartialTick, final LevelRenderState levelRenderState) {
		Vec3 cameraPos = camera.position();
		double camX = cameraPos.x();
		double camY = cameraPos.y();
		double camZ = cameraPos.z();
		PoseStack poseStack = new PoseStack();
		long chunkFadeDuration = Util.toMillis(this.minecraft.options.chunkSectionFadeInTime().get());

		for (SectionRenderDispatcher.RenderSection section : this.levelRenderer.visibleSections()) {
			BiList<BlockPos, BlockState> renderableSpecialBlocks = section.getSectionMesh() instanceof CompiledSectionMeshDuck duck ? duck.mmodding$specialBlocks() : BiList.create();
			if (!renderableSpecialBlocks.isEmpty() && !(section.getVisibility(Util.getMillis(), chunkFadeDuration) < 0.3F)) {
				for (Pair<BlockPos, BlockState> specialBlock : renderableSpecialBlocks) {
					BlockPos blockPos = specialBlock.first();
					assert this.level != null;
					SortedSet<BlockDestructionProgress> progresses = this.level.destructionProgress().get(blockPos.asLong());
					ModelFeatureRenderer.CrumblingOverlay breakProgress;
					if (progresses != null && !progresses.isEmpty()) {
						poseStack.pushPose();
						poseStack.translate(blockPos.getX() - camX, blockPos.getY() - camY, blockPos.getZ() - camZ);
						breakProgress = new ModelFeatureRenderer.CrumblingOverlay(progresses.last().getProgress(), poseStack.last());
						poseStack.popPose();
					} else {
						breakProgress = null;
					}

					SpecialBlockRenderState state = SpecialBlockRenderDispatcherImpl.INSTANCE.tryExtractRenderState(this.level, blockPos, specialBlock.second(), deltaPartialTick, breakProgress);
					if (state != null) {
						if (levelRenderState.getData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES) == null) {
							levelRenderState.setData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES, new ArrayList<>());
						}
						Objects.requireNonNull(levelRenderState.getData(MModdingRenderingSetup.SPECIAL_BLOCK_RENDER_STATES)).add(state);
					}
				}
			}
		}
	}
}
