package com.mmodding.library.rendering.test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

public class RenderingTests implements ModInitializer {

	public static final ResourceKey<Item> TEST_CAP_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_cap"));
	public static final ResourceKey<Item> TEST_SUIT_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_suit"));

	public static final Item TEST_CAP = new Item(new Item.Properties().equippable(EquipmentSlot.HEAD).setId(TEST_CAP_KEY));
	public static final Item TEST_SUIT = new Item(new Item.Properties().equippable(EquipmentSlot.CHEST).setId(TEST_SUIT_KEY));

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ITEM, TEST_CAP_KEY, TEST_CAP);
		Registry.register(BuiltInRegistries.ITEM, TEST_SUIT_KEY, TEST_SUIT);
	}
}
