package com.mmodding.library.enchantment.api.family;

import com.mmodding.library.java.api.list.filter.FilterList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

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
	public List<ChatFormatting> getFormattings(Holder<Enchantment> enchantment) {
		return List.of(enchantment.is(EnchantmentTags.CURSE) ? ChatFormatting.RED : ChatFormatting.GRAY);
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
	public Item getEnchantedBookItem() {
		return Items.ENCHANTED_BOOK;
	}
}
