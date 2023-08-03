package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.FishingRodItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomFishingRodItem extends FishingRodItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomFishingRodItem(Settings settings) {
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
