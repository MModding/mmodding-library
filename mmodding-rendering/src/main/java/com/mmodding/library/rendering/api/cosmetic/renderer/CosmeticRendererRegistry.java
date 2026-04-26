package com.mmodding.library.rendering.api.cosmetic.renderer;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.impl.cosmetic.renderer.*;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public final class CosmeticRendererRegistry {

	public CosmeticRendererRegistry() {
		throw new IllegalStateException("CosmeticRendererRegistry class only contains static definitions");
	}

	/**
	 * Rendering {@link Cosmetic} stuff as a cap-like.
	 */
	public static void registerCapRenderer(Cosmetic cosmetic, HeadAnchor anchor, Item... items) {
		CosmeticRendererRegistry.registerRenderer(cosmetic, (c, context) -> new CapCosmeticRenderer(c, anchor, context), items);
	}

	/**
	 * Rendering {@link Cosmetic} stuff as a suit-like.
	 * <br>A suit model should have three named parts: <code>body</code>, <code>left_sleeve</code> and <code>right_sleeve</code>.
	 * <br>But, the model can actually miss either <code>body</code> or both <code>left_sleeve</code> and <code>right_sleeve</code> if you only want the body or the sleeves to render.
	 */
	public static void registerSuitRenderer(Cosmetic cosmetic, Item... items) {
		CosmeticRendererRegistry.registerRenderer(cosmetic, SuitCosmeticRenderer::new, items);
	}

	/**
	 * Rendering {@link Cosmetic} stuff as a pants-like.
	 * <br>A pants model should have three named parts: <code>junction</code>, <code>left_legging</code> and <code>right_legging</code>.
	 * <br>But, the model can actually miss either <code>junction</code> or both <code>left_legging</code> and <code>right_legging</code> if you only want the junction or the leggings to render.
	 */
	public static void registerPantsRenderer(Cosmetic cosmetic, Item... items) {
		CosmeticRendererRegistry.registerRenderer(cosmetic, PantsCosmeticRenderer::new, items);
	}

	/**
	 * Rendering {@link Cosmetic} stuff as a shoes-like.
	 * <br>A shoes model should have two named parts: <code>left_shoe</code> and <code>right_shoe</code>.
	 */
	public static void registerShoesRenderer(Cosmetic cosmetic, Item... items) {
		CosmeticRendererRegistry.registerRenderer(cosmetic, ShoesCosmeticRenderer::new, items);
	}

	/**
	 * Registers the cosmetic renderer for the specified items.
	 * @param cosmetic the cosmetic
	 * @param factory the renderer factory
	 * @param items the items
	 * @throws IllegalArgumentException if an item already has a registered armor renderer
	 * @throws NullPointerException if either an item or the factory is null
	 * @apiNote If you are making your own {@link CosmeticRenderer}, please consider using this instead of {@link ArmorRenderer#register(ArmorRenderer.Factory, ItemLike...)}.
	 */
	public static void registerRenderer(Cosmetic cosmetic, CosmeticRenderer.Factory factory, Item... items) {
		CosmeticRendererRegistryImpl.registerRenderer(cosmetic, factory, items);
	}
}
