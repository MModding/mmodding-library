package com.mmodding.library.enchantment.impl.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.java.api.list.filter.FilterList;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;

@ApiStatus.Internal
public class EnchantmentFamilyBuilderImpl implements EnchantmentFamily.Builder {

	private final String qualifier;

	private Supplier<EnchantedBookItem> bookItem = () -> (EnchantedBookItem) Items.ENCHANTED_BOOK;
	private Component prefix = Component.empty();
	private Function<AdvancedEnchantment, List<ChatFormatting>> formattings = enchantment -> List.of(enchantment.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY);
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
	public EnchantmentFamily.Builder prefix(Component prefix) {
		this.prefix = prefix;
		return this;
	}

	@Override
	public EnchantmentFamily.Builder formattings(Function<AdvancedEnchantment, List<ChatFormatting>> formattings) {
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
