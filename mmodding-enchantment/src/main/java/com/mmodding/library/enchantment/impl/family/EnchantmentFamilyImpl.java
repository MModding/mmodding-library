package com.mmodding.library.enchantment.impl.family;

import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

@ApiStatus.Internal
public class EnchantmentFamilyImpl implements EnchantmentFamily {

	private final String qualifier;
	private final Supplier<Item> bookItem;
	private final Component prefix;
	private final Function<Holder<Enchantment>, List<ChatFormatting>> formattings;
	private final boolean inEnchantingTable;
	private final FilterList<EnchantmentFamily> familyCompatibilities;

	EnchantmentFamilyImpl(String qualifier, Supplier<Item> bookItem, Component prefix, Function<Holder<Enchantment>, List<ChatFormatting>> formattings, boolean inEnchantingTable, FilterList<EnchantmentFamily> familyCompatibilities) {
		this.qualifier = qualifier;
		this.bookItem = bookItem;
		this.prefix = prefix;
		this.formattings = formattings;
		this.inEnchantingTable = inEnchantingTable;
		this.familyCompatibilities = familyCompatibilities;
	}

	@Override
	public String getQualifier() {
		return this.qualifier;
	}

	@Override
	public Item getEnchantedBookItem() {
		return this.bookItem.get();
	}

	@Override
	public Component getPrefix() {
		return this.prefix;
	}

	@Override
	public List<ChatFormatting> getFormattings(Holder<Enchantment> enchantment) {
		return this.formattings.apply(enchantment);
	}

	@Override
	public boolean isObtainableInEnchantingTable() {
		return this.inEnchantingTable;
	}

	@Override
	public FilterList<EnchantmentFamily> getFamilyCompatibilities() {
		return this.familyCompatibilities;
	}
}
