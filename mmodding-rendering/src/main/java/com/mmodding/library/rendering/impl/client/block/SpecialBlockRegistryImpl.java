package com.mmodding.library.rendering.impl.client.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderer;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class SpecialBlockRegistryImpl {

	private static final Map<Block, SpecialBlockRendererProvider<?>> PROVIDERS = Maps.newHashMap();

	public static boolean isRegistered(Block block) {
		return PROVIDERS.containsKey(block);
	}

	public static <S extends SpecialBlockRenderState> void register(Block block, SpecialBlockRendererProvider<S> provider) {
		PROVIDERS.put(block, provider);
	}

	public static Map<Block, SpecialBlockRenderer<?>> createSpecialBlockRenderers(final SpecialBlockRendererProvider.Context context) {
		ImmutableMap.Builder<Block, SpecialBlockRenderer<?>> result = ImmutableMap.builder();
		PROVIDERS.forEach((type, provider) -> {
			try {
				result.put(type, provider.create(context));
			} catch (Exception e) {
				throw new IllegalStateException("Failed to create model for " + BuiltInRegistries.BLOCK.getKey(type), e);
			}
		});
		return result.build();
	}
}
