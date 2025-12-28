package com.mmodding.library.enchantment.impl.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Internal
public class EnchantmentFamilyImpl implements EnchantmentFamily {

	private final String qualifier;
	private final Supplier<EnchantedBookItem> bookItem;
	private final Text prefix;
	private final Function<AdvancedEnchantment, List<Formatting>> formattings;
	private final boolean inEnchantingTable;
	private final FilterList<EnchantmentFamily> familyCompatibilities;

	EnchantmentFamilyImpl(String qualifier, Supplier<EnchantedBookItem> bookItem, Text prefix, Function<AdvancedEnchantment, List<Formatting>> formattings, boolean inEnchantingTable, FilterList<EnchantmentFamily> familyCompatibilities) {
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
	public EnchantedBookItem getBookItem() {
		return this.bookItem.get();
	}

	@Override
	public Text getPrefix() {
		return this.prefix;
	}

	@Override
	public List<Formatting> getFormattings(AdvancedEnchantment enchantment) {
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
