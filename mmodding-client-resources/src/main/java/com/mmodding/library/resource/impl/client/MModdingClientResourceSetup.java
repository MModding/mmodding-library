package com.mmodding.library.resource.impl.client;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.resource.impl.client.model.data.BlockEntityModelReloader;
import com.mmodding.library.resource.impl.client.model.data.EntityModelReloader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

@Environment(EnvType.CLIENT)
public class MModdingClientResourceSetup implements ClientModInitializer {

	private static final Identifier ENTITY_MODELS = MModdingLibrary.createId("entity_models");
	private static final Identifier BLOCK_ENTITY_MODELS = MModdingLibrary.createId("block_entity_models");

	@Override
	public void onInitializeClient() {
		ResourceLoader clientResourceLoader = ResourceLoader.get(PackType.CLIENT_RESOURCES);

		clientResourceLoader.registerReloadListener(ENTITY_MODELS, EntityModelReloader.INSTANCE);
		clientResourceLoader.addListenerOrdering(ENTITY_MODELS, ResourceReloaderKeys.Client.ENTITY_RENDER_DISPATCHER);

		clientResourceLoader.registerReloadListener(BLOCK_ENTITY_MODELS, BlockEntityModelReloader.INSTANCE);
		clientResourceLoader.addListenerOrdering(BLOCK_ENTITY_MODELS, ResourceReloaderKeys.Client.BLOCK_ENTITY_RENDER_DISPATCHER);
	}
}
