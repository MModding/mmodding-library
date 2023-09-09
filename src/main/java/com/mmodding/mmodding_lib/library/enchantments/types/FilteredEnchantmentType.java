package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.utils.FilterList;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class FilteredEnchantmentType extends TableExclusionEnchantmentType {

	private final FilterList<EnchantmentType> filterList;

	FilteredEnchantmentType(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable, FilterList<EnchantmentType> filterList) {
		super(qualifier, prefix, enchantedBookSettings, inEnchantingTable);
		this.filterList = filterList;
	}

	@Override
	public FilterList<EnchantmentType> typeCompatibilities() {
		return this.filterList;
	}
}
