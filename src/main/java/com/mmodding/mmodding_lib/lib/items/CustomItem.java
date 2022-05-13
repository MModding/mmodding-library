package com.mmodding.mmodding_lib.lib.items;

import net.minecraft.item.Item;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomItem extends Item implements ItemRegistrable {
    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
