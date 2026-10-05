package com.mmodding.library.rendering.impl.client;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.rendering.api.client.block.SpecialBlockRenderState;
import com.mmodding.library.rendering.impl.client.block.SpecialBlockRenderDispatcherImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

import java.util.List;

public class MModdingRenderingSetup implements ClientModInitializer {

	private static final Identifier SPECIAL_BLOCKS_RENDER_DISPATCHER = MModdingLibrary.createId("special_blocks_render_dispatcher");

	public static final RenderStateDataKey<List<SpecialBlockRenderState>> SPECIAL_BLOCK_RENDER_STATES = RenderStateDataKey.create();

	@Override
	public void onInitializeClient() {
		ResourceLoader clientResourceLoader = ResourceLoader.get(PackType.CLIENT_RESOURCES);
		clientResourceLoader.registerReloadListener(SPECIAL_BLOCKS_RENDER_DISPATCHER, SpecialBlockRenderDispatcherImpl.INSTANCE);
		clientResourceLoader.addListenerOrdering(ResourceReloaderKeys.Client.BLOCK_ENTITY_RENDER_DISPATCHER, SPECIAL_BLOCKS_RENDER_DISPATCHER);
	}
}
