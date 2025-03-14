package com.mmodding.library.item.test;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class ItemTests {

	public static final ItemCategory CATEGORY = ItemCategory.create(Reference.createId("", ""), settings -> {});

	public static final Item FIRST_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static final Item SECOND_ITEM = new Item(new FabricItemSettings()).setCategory(CATEGORY);

	public static void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.ITEM).execute(pusher -> {
			FIRST_ITEM.register(pusher.createId("first_item"));
			SECOND_ITEM.register(pusher.createId("second_item"));
		});
	}
}
