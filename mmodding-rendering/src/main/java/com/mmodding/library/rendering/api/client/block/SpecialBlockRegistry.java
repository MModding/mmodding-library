package com.mmodding.library.rendering.api.client.block;

import com.mmodding.library.rendering.impl.client.block.SpecialBlockRegistryImpl;
import net.minecraft.world.level.block.Block;

public class SpecialBlockRegistry {

	private SpecialBlockRegistry() {}

	/**
	 * Registers a special block renderer provider for a specified block type.
	 * @param block the block type
	 * @param provider the provider
	 * @param <S> the state class type
	 */
	public static <S extends SpecialBlockRenderState> void register(Block block, SpecialBlockRendererProvider<S> provider) {
		SpecialBlockRegistryImpl.register(block, provider);
	}
}
