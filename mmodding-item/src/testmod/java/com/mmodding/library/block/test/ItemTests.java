package com.mmodding.library.block.test;

import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ItemTests {

	public static final Item FIRST_ITEM = new Item(new Item.Properties());

	public static final Item SECOND_ITEM = new Item(new Item.Properties());

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.ITEM, factory -> {
			factory.register("first_item", FIRST_ITEM);
			factory.register("second_item", SECOND_ITEM);
		});
	}
}
