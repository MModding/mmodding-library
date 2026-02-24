package com.mmodding.library.block.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemTests {

	public static final ItemCategory CATEGORY = ItemCategory.create(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("", "")), settings -> {});

	public static final Item FIRST_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static final Item SECOND_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static void register(AdvancedContainer mod) {
		mod.register(Registries.ITEM, factory -> {
			factory.register("first_item", FIRST_ITEM);
			factory.register("second_item", SECOND_ITEM);
		});
	}
}
