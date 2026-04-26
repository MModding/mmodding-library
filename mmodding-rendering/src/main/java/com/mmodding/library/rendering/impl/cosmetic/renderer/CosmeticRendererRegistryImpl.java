package com.mmodding.library.rendering.impl.cosmetic.renderer;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.cosmetic.renderer.CosmeticRenderer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class CosmeticRendererRegistryImpl {

	// These are only used for mod integration purposes. It's this or using a fabric internal.
	// But I hope fabric lets me get the armor renderer of an item in the future.
	public static final Map<Item, ArmorRenderer> ENTRIES = new Object2ObjectOpenHashMap<>();
	private static final Map<Item, ArmorRenderer.Factory> FACTORIES = new Object2ObjectOpenHashMap<>();

	public static void registerRenderer(Cosmetic cosmetic, CosmeticRenderer.Factory factory, Item... items) {
		ArmorRenderer.Factory armorRendererFactory = context -> factory.createCosmeticRenderer(cosmetic, context);
		ArmorRenderer.register(armorRendererFactory, items);
		for (Item item : items) {
			FACTORIES.put(item, armorRendererFactory);
		}
	}

	public static void createArmorRenderers(EntityRendererProvider.Context context) {
		ENTRIES.clear();
		FACTORIES.forEach((item, factory) -> ENTRIES.put(item, factory.createArmorRenderer(context)));
	}
}
