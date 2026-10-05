package com.mmodding.library.rendering.api.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * A variant of <code>BlockEntityRenderer</code>, without block entities,
 * for specific rendering behavior on a per-block levels, without needing
 * them to have block entities.
 * @param <S> the render state type
 */
public interface SpecialBlockRenderer<S extends SpecialBlockRenderState> {

	S createRenderState();

	default void extractRenderState(BlockAndLightGetter level, BlockPos pos, BlockState blockState, final S state, final float partialTicks, final Vec3 cameraPosition, final ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		SpecialBlockRenderState.extractBase(level, pos, blockState, state, breakProgress);
	}

	void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera);

	default boolean shouldRenderOffScreen() {
		return false;
	}

	default int getViewDistance() {
		return 64;
	}

	default boolean shouldRender(BlockPos pos, BlockState blockState, final Vec3 cameraPosition) {
		return Vec3.atCenterOf(pos).closerThan(cameraPosition, this.getViewDistance());
	}
}
