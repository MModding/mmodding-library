package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.ArrowItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomArrowItem extends ArrowItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomArrowItem(Settings settings) {
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
