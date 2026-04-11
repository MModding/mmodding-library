package com.mmodding.library.item.api.catalog;

import java.util.function.Supplier;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class AdvancedBowItem extends BowItem {

	private final Supplier<Item> defaultArrowItem;

	public AdvancedBowItem(Properties settings) {
		this(() -> Items.ARROW, settings);
	}

	public AdvancedBowItem(Supplier<Item> defaultArrowItem, Properties settings) {
		super(settings);
		this.defaultArrowItem = defaultArrowItem;
	}

	public Item getDefaultArrowItem() {
		return this.defaultArrowItem.get();
	}
}
