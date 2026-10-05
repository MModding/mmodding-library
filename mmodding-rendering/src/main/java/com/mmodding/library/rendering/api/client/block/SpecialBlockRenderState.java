package com.mmodding.library.rendering.api.client.block;

import net.minecraft.CrashReportCategory;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * A render state for instanced block in a level, not requiring a block entity.
 */
public class SpecialBlockRenderState {

	public BlockPos blockPos = BlockPos.ZERO;
	public BlockState blockState = Blocks.AIR.defaultBlockState();
	public int lightCoords;
	public ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress;

	public static void extractBase(BlockAndLightGetter level, BlockPos pos, BlockState blockState, SpecialBlockRenderState state, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		state.blockPos = pos;
		state.blockState = blockState;
		state.lightCoords = LightCoordsUtil.getLightCoords(level, pos);
		state.breakProgress = breakProgress;
	}

	public void fillCrashReportCategory(final CrashReportCategory category) {
		category.setDetail("SpecialBlockRenderState", this.getClass().getCanonicalName());
		category.setDetail("Position", this.blockPos);
		category.setDetail("Block state", this.blockState::toString);
	}
}
