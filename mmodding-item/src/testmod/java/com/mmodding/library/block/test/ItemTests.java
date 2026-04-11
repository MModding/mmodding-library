package com.mmodding.library.block.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemTests {

	public static final ItemCategory CATEGORY = ItemCategory.create(ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("", "")), settings -> {});

	public static final Item FIRST_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static final Item SECOND_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.ITEM, factory -> {
			factory.register("first_item", FIRST_ITEM);
			factory.register("second_item", SECOND_ITEM);
		});
	}
}
