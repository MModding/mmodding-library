package com.mmodding.library.item.test;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import com.mmodding.library.item.api.group.ItemGroupQualifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ItemTests implements DefaultContentHolder {

	public static final ItemGroupQualifier QUALIFIER = ItemGroupQualifier.create(Reference.createId("", ""), settings -> {});

	public static final Item FIRST_ITEM = new Item(new QuiltItemSettings()).applyQualifier(QUALIFIER);

	public static final Item SECOND_ITEM = new Item(new QuiltItemSettings()).applyQualifier(QUALIFIER);

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.ITEM).execute(init -> {
			FIRST_ITEM.register(init.createId("first_item"));
			SECOND_ITEM.register(init.createId("second_item"));
		});
	}
}
