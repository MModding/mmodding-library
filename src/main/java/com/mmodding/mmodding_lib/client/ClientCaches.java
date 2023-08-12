package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.config.StaticConfig;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import net.minecraft.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class ClientCaches {

	public static final Caches.Client<String, StaticConfig> CONFIGS = new Caches.Client<>("Configs", "Qualifier", "Config");

	public static final Caches.Client<Item, GlintPack> GLINT_PACKS = new Caches.Client<>("Glint Packs", "Item", "Glint Pack");

	public static final Caches.Client<Item, GlintPackView> GLINT_PACK_OVERRIDES = new Caches.Client<>("Glint Pack Overrides", "Item", "Glint Pack View");
}
