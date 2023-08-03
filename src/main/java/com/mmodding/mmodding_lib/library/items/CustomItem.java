package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.Item;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomItem extends Item implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomItem(Settings settings) {
        super(settings);
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
