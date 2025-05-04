package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.config.StaticConfig;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public class ClientCaches {

	public static final Caches.Client<String, StaticConfig> CONFIGS = new Caches.Client<>("Configs", "Qualifier", "Config");

	public static final Caches.Client<Item, GlintPackView> GLINT_PACK_OVERRIDES = new Caches.Client<>("Glint Pack Overrides", "Item", "Glint Pack View");
}
