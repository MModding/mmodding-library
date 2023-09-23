package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBowItem extends BowItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final Item defaultArrowItem;

	public CustomBowItem(Settings settings) {
		this(Items.ARROW, settings);
	}

    public CustomBowItem(Item defaultArrowItem, Settings settings) {
		super(settings);
		this.defaultArrowItem = defaultArrowItem;
	}

	public Item getDefaultArrowItem() {
		return this.defaultArrowItem;
	}

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
