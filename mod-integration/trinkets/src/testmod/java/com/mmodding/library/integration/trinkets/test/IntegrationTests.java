package com.mmodding.library.integration.trinkets.test;

import com.mmodding.library.core.api.registry.IdentifierUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.List;

// Testmod is almost the same as MModding Rendering Tesmod (cosmetic-related parts).
public class IntegrationTests implements ModInitializer {

	public static final ResourceKey<Item> TEST_CAP_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_cap"));
	public static final ResourceKey<Item> TEST_SUIT_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_suit"));
	public static final ResourceKey<Item> TEST_PANTS_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_pants"));
	public static final ResourceKey<Item> TEST_SHOES_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("mmodding_rendering_testmod", "test_shoes"));

	public static final Item TEST_CAP = new Item(new Item.Properties().equippable(EquipmentSlot.HEAD).trinketSlots(List.of("head/face")).setId(TEST_CAP_KEY));
	public static final Item TEST_SUIT = new Item(new Item.Properties().equippable(EquipmentSlot.CHEST).trinketSlots(List.of("chest/cape")).setId(TEST_SUIT_KEY));
	public static final Item TEST_PANTS = new Item(new Item.Properties().equippable(EquipmentSlot.LEGS).trinketSlots(List.of("legs/belt")).setId(TEST_PANTS_KEY));
	public static final Item TEST_SHOES = new Item(new Item.Properties().equippable(EquipmentSlot.FEET).trinketSlots(List.of("feet/shoes")).setId(TEST_SHOES_KEY));

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ITEM, TEST_CAP_KEY, TEST_CAP);
		Registry.register(BuiltInRegistries.ITEM, TEST_SUIT_KEY, TEST_SUIT);
		Registry.register(BuiltInRegistries.ITEM, TEST_PANTS_KEY, TEST_PANTS);
		Registry.register(BuiltInRegistries.ITEM, TEST_SHOES_KEY, TEST_SHOES);
	}

	public static Identifier createTexture(String path) {
		return IdentifierUtil.texture("mod_integration_mmodding_trinkets_testmod", path);
	}
}
