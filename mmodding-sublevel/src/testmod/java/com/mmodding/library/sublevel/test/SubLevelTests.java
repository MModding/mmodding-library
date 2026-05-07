package com.mmodding.library.sublevel.test;

import com.mmodding.library.sublevel.api.SublevelType;
import com.mmodding.library.sublevel.api.Sublevels;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class SubLevelTests implements ModInitializer {

	public static final SublevelType<Player> PLAYER_ATTACHED = Sublevels.createPlayerAttachedType(createKey(Registries.LEVEL_STEM, "player_attached"), 4);

	public static final ResourceKey<Item> TELEPORT_ITEM_KEY = createKey(Registries.ITEM, "teleport_item");

	public static final Item TELEPORT_ITEM = Registry.register(BuiltInRegistries.ITEM, TELEPORT_ITEM_KEY, new TeleportItem(new Item.Properties().setId(TELEPORT_ITEM_KEY)));

	public static String namespace() {
		return "mmodding_sublevel_testmod";
	}

	public static Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}

	public static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, createId(path));
	}

	@Override
	public void onInitialize() {}
}
