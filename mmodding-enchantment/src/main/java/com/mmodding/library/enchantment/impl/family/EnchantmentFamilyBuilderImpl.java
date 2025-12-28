package com.mmodding.library.enchantment.impl.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.java.api.list.filter.FilterList;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Internal
public class EnchantmentFamilyBuilderImpl implements EnchantmentFamily.Builder {

	private final String qualifier;

	private Supplier<EnchantedBookItem> bookItem = () -> (EnchantedBookItem) Items.ENCHANTED_BOOK;
	private Text prefix = Text.empty();
	private Function<AdvancedEnchantment, List<Formatting>> formattings = enchantment -> List.of(enchantment.isCursed() ? Formatting.RED : Formatting.GRAY);
	private boolean inEnchantingTable = true;
	private FilterList<EnchantmentFamily> familyCompatibilities = FilterList.always();

	public EnchantmentFamilyBuilderImpl(String qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public EnchantmentFamily.Builder bookItem(Supplier<EnchantedBookItem> bookItem) {
		this.bookItem = bookItem;
		return this;
	}

	@Override
	public EnchantmentFamily.Builder prefix(Text prefix) {
		this.prefix = prefix;
		return this;
	}

	@Override
	public EnchantmentFamily.Builder formattings(Function<AdvancedEnchantment, List<Formatting>> formattings) {
		this.formattings = formattings;
		return this;
	}

	@Override
	public EnchantmentFamily.Builder setInEnchantingTable(boolean inEnchantingTable) {
		this.inEnchantingTable = inEnchantingTable;
		return this;
	}

	@Override
	public EnchantmentFamily.Builder familyCompatibilities(FilterList<EnchantmentFamily> familyCompatibilities) {
		this.familyCompatibilities = familyCompatibilities;
		return this;
	}

	@Override
	public EnchantmentFamily build() {
		return new EnchantmentFamilyImpl(this.qualifier, this.bookItem, this.prefix, this.formattings, this.inEnchantingTable, this.familyCompatibilities);
	}
}
