package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantedBookItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.FilterList;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public interface EnchantmentType {

	EnchantmentType DEFAULT = new DefaultEnchantmentType();

	static EnchantmentType of(String qualifier, Text prefix) {
		return EnchantmentType.of(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
	}

	static EnchantmentType of(String qualifier, Text prefix, boolean inEnchantingTable) {
		return new TableExclusionEnchantmentType(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), inEnchantingTable);
	}

	static EnchantmentType of(String qualifier, Text prefix, boolean inEnchantingTable, FilterList<EnchantmentType> filter) {
		return new FilteredEnchantmentType(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), inEnchantingTable, filter);
	}

	static EnchantmentType of(String qualifier, Text prefix, Item.Settings enchantedBookSettings) {
		return new SimpleEnchantmentType(qualifier, prefix, enchantedBookSettings);
	}

	static EnchantmentType of(String qualifier, Text prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable) {
		return new TableExclusionEnchantmentType(qualifier, prefix, enchantedBookSettings, inEnchantingTable);
	}

	static EnchantmentType of(String qualifier, Text prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable, FilterList<EnchantmentType> filter) {
		return new FilteredEnchantmentType(qualifier, prefix, enchantedBookSettings, inEnchantingTable, filter);
	}

	String getQualifier();

	Text getPrefix();

	EnchantedBookItem getEnchantedBook();

	default boolean isInEnchantingTable() {
		return true;
	}

	default FilterList<EnchantmentType> typeCompatibilities() {
		return FilterList.always();
	}

	default void register(Identifier identifier) {
		if (this.getEnchantedBook() instanceof CustomEnchantedBookItem item) {
			item.register(identifier);
		}
	}
}
