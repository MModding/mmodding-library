package com.mmodding.library.enchantment.api.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * The {@link DefaultEnchantmentFamily} public implementation.
 */
public class DefaultEnchantmentFamily implements EnchantmentFamily {

	DefaultEnchantmentFamily() {}

	@Override
	public String getQualifier() {
		return "default";
	}

	@Override
	public Text getPrefix() {
		return Text.empty();
	}

	@Override
	public List<Formatting> getFormattings(AdvancedEnchantment enchantment) {
		return List.of(enchantment.isCursed() ? Formatting.RED : Formatting.GRAY);
	}

	@Override
	public boolean isObtainableInEnchantingTable() {
		return true;
	}

	@Override
	public FilterList<EnchantmentFamily> getFamilyCompatibilities() {
		return FilterList.always();
	}

	@Override
	public EnchantedBookItem getBookItem() {
		return (EnchantedBookItem) Items.ENCHANTED_BOOK;
	}
}
