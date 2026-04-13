package com.mmodding.library.block.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.item.api.category.ItemCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ItemTests {

	public static final ItemCategory CATEGORY = ItemCategory.create(ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("", "")), settings -> {});

	public static final Item FIRST_ITEM = new Item(new Item.Properties()).setCategory(CATEGORY);

	public static final Item SECOND_ITEM = new Item(new Item.Properties()).setCategory(CATEGORY);

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.ITEM, factory -> {
			factory.register("first_item", FIRST_ITEM);
			factory.register("second_item", SECOND_ITEM);
		});
	}
}
