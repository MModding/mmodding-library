package com.mmodding.library.enchantment.api.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.java.api.list.filter.FilterList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;

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
	public Component getPrefix() {
		return Component.empty();
	}

	@Override
	public List<ChatFormatting> getFormattings(AdvancedEnchantment enchantment) {
		return List.of(enchantment.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY);
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
