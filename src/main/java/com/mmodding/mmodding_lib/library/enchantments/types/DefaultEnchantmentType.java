package com.mmodding.mmodding_lib.library.enchantments.types;

import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;

public class DefaultEnchantmentType implements EnchantmentType {

    DefaultEnchantmentType() {}

    @Override
    public String getQualifier() {
        return "default";
    }

    @Override
    public Prefix getPrefix() {
        return Prefix.of("");
    }

    @Override
    public EnchantedBookItem getEnchantedBook() {
        return (EnchantedBookItem) Items.ENCHANTED_BOOK;
    }
}
