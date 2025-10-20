package com.mmodding.library.item.api.catalog;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public class AdvancedBowItem extends BowItem {

	private final Supplier<Item> defaultArrowItem;

	public AdvancedBowItem(Settings settings) {
		this(() -> Items.ARROW, settings);
	}

	public AdvancedBowItem(Supplier<Item> defaultArrowItem, Settings settings) {
		super(settings);
		this.defaultArrowItem = defaultArrowItem;
	}

	public Item getDefaultArrowItem() {
		return this.defaultArrowItem.get();
	}
}
