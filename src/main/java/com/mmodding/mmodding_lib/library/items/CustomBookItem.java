package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import net.minecraft.item.BookItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBookItem extends BookItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private final EnchantmentType type;

    public CustomBookItem(Settings settings) {
        this(EnchantmentType.DEFAULT, settings);
    }

    public CustomBookItem(EnchantmentType type, Settings settings) {
        super(settings);
        this.type = type;
    }

    public EnchantmentType getType() {
        return this.type;
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
