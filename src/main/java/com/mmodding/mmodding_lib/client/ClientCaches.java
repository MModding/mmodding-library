package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import net.minecraft.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;

@ClientOnly
@ApiStatus.Internal
public class ClientCaches {

	public static final ClientCache<Item, GlintPack> GLINT_PACKS = new ClientCache<>("Glint Packs", "Item", "Glint Pack");

	public static void debugCaches() {
		GLINT_PACKS.debug();
	}

	public static void avoidCaches() {
		GLINT_PACKS.clear();
	}

	public static class ClientCache<K, V> extends HashMap<K, V> {

		private final String cache;
		private final String key;
		private final String value;

		public ClientCache(String cache, String key, String value) {
			this.cache = cache;
			this.key = key;
			this.value = value;
		}

		public void debug() {
			System.out.println("Client Cache {" + this.cache + "} :");
			this.forEach((key, value) -> System.out.println(
				"- " + this.key + " : " + key + " | " + this.value + " : " + value
			));
		}
	}
}
