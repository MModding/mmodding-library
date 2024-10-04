package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.utils.FilterList;
import com.mmodding.mmodding_lib.library.texts.Prefix;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

import java.util.List;

public class DefaultEnchantmentType implements EnchantmentType {

    DefaultEnchantmentType() {}

    @Override
    public String getQualifier() {
        return "default";
    }

    @Override
    public Prefix getPrefix() {
        return Prefix.empty();
    }

	@Override
	public List<Formatting> getFormattings(CustomEnchantment enchantment) {
		return List.of(enchantment.isCursed() ? Formatting.RED : Formatting.GRAY);
	}

	@Override
	public boolean canBeObtainedThroughEnchantingTable() {
		return true;
	}

	@Override
	public FilterList<EnchantmentType> getTypeCompatibilities() {
		return FilterList.always();
	}

	@Override
    public EnchantedBookItem getBookItem() {
        return (EnchantedBookItem) Items.ENCHANTED_BOOK;
    }
}
