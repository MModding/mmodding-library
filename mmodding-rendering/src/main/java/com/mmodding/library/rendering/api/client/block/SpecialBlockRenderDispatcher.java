package com.mmodding.library.rendering.api.client.block;

import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public interface SpecialBlockRenderDispatcher {

	<S extends SpecialBlockRenderState> @Nullable SpecialBlockRenderer<S> getRenderer(BlockState blockState);

	<S extends SpecialBlockRenderState> @Nullable SpecialBlockRenderer<S> getRenderer(S state);
}
