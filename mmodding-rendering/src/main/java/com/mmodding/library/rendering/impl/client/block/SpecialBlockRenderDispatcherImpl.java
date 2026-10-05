package com.mmodding.library.rendering.impl.client.block;

import com.google.common.collect.ImmutableMap;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderDispatcher;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderer;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class SpecialBlockRenderDispatcherImpl implements SpecialBlockRenderDispatcher, ResourceManagerReloadListener {

	public static final SpecialBlockRenderDispatcherImpl INSTANCE = new SpecialBlockRenderDispatcherImpl();

	private Map<Block, SpecialBlockRenderer<?>> renderers = ImmutableMap.of();
	private Vec3 cameraPos;

	@SuppressWarnings("unchecked")
	public <S extends SpecialBlockRenderState> @Nullable SpecialBlockRenderer<S> getRenderer(BlockState blockState) {
		return (SpecialBlockRenderer<S>) this.renderers.get(blockState.getBlock());
	}

	@SuppressWarnings("unchecked")
	public <S extends SpecialBlockRenderState> @Nullable SpecialBlockRenderer<S> getRenderer(S state) {
		return (SpecialBlockRenderer<S>) this.renderers.get(state.blockState.getBlock());
	}

	public void prepare(final Vec3 cameraPos) {
		this.cameraPos = cameraPos;
	}

	public <S extends SpecialBlockRenderState> @Nullable S tryExtractRenderState(BlockAndLightGetter level, BlockPos pos, BlockState blockState, float partialTicks, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		SpecialBlockRenderer<S> renderer = this.getRenderer(blockState);
		if (renderer == null) {
			return null;
		}

		if (!renderer.shouldRender(pos, blockState, this.cameraPos)) {
			return null;
		}

		Vec3 cameraPosition = this.cameraPos;
		S state = renderer.createRenderState();
		renderer.extractRenderState(level, pos, blockState, state, partialTicks, cameraPosition, breakProgress);
		return state;
	}

	public <S extends SpecialBlockRenderState> void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		SpecialBlockRenderer<S> renderer = this.getRenderer(state);
		if (renderer != null) {
			try {
				renderer.submit(state, poseStack, submitNodeCollector, camera);
			} catch (Throwable t) {
				CrashReport report = CrashReport.forThrowable(t, "Rendering Special Block");
				CrashReportCategory category = report.addCategory("Special Block Details");
				state.fillCrashReportCategory(category);
				throw new ReportedException(report);
			}
		}
	}

	@Override
	public void onResourceManagerReload(final ResourceManager resourceManager) {
		SpecialBlockRendererProvider.Context context = new SpecialBlockRendererProvider.Context(
			this,
			new BlockModelResolver(Minecraft.getInstance().getModelManager()),
			Minecraft.getInstance().getItemModelResolver(),
			Minecraft.getInstance().getEntityRenderDispatcher(),
			Minecraft.getInstance().getModelManager().entityModels().get(),
			Minecraft.getInstance().font,
			Minecraft.getInstance().getAtlasManager(),
			Minecraft.getInstance().playerSkinRenderCache()
		);
		this.renderers = SpecialBlockRegistryImpl.createSpecialBlockRenderers(context);
	}
}
