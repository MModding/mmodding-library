package com.mmodding.library.enchantment.api.family;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.impl.family.EnchantmentFamilyBuilderImpl;
import com.mmodding.library.java.api.list.filter.FilterList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;

/**
 * An {@link EnchantmentFamily} can be defined as an attribute of an {@link AdvancedEnchantment} to classify them to.
 * Those families define generalized attributes applying to all their {@link AdvancedEnchantment} objects such as an
 * {@link EnchantedBookItem}, family compatibilities or visual details.
 */
public interface EnchantmentFamily {

	/**
	 * The default enchantment family.
	 */
	EnchantmentFamily DEFAULT = new DefaultEnchantmentFamily();

	/**
	 * Allows to create a new {@link EnchantmentFamily} without much parameterizing.
	 * @param qualifier the enchantment family qualifier
	 * @return the newly created enchantment family
	 */
	static EnchantmentFamily of(String qualifier) {
		return EnchantmentFamily.builder(qualifier).build();
	}

	/**
	 * Allows to create a new {@link EnchantmentFamily.Builder}.
	 * @param qualifier the enchantment family qualifier
	 * @return a builder to create the enchantment family
	 */
	static Builder builder(String qualifier) {
		return new EnchantmentFamilyBuilderImpl(qualifier);
	}

	/**
	 * The qualifier of the {@link EnchantmentFamily}.
	 * @return the qualifier
	 */
	String getQualifier();

	/**
	 * The associated {@link EnchantedBookItem} to the {@link EnchantmentFamily}.
	 * @return the enchanted book item
	 */
	EnchantedBookItem getBookItem();

	/**
	 * The prefix displayed before the name of every {@link AdvancedEnchantment} of the {@link EnchantmentFamily}.
	 * @return the prefix
	 */
	Component getPrefix();

	/**
	 * A list of {@link ChatFormatting} applied to the name of an {@link AdvancedEnchantment} of the {@link EnchantmentFamily}.
	 * @param enchantment the advanced enchantment
	 * @return the formattings of the advanced enchantment
	 */
	List<ChatFormatting> getFormattings(AdvancedEnchantment enchantment);

	/**
	 * Indicates if the {@link AdvancedEnchantment} of the {@link EnchantmentFamily} can be obtained through enchanting tables.
	 * @return a boolean indicating if it can be obtained in enchanting tables
	 */
	boolean isObtainableInEnchantingTable();

	/**
	 * A {@link FilterList} allowing to establish the compatibilities between {@link EnchantmentFamily} objects.
	 * @return the filter list
	 */
	FilterList<EnchantmentFamily> getFamilyCompatibilities();

	/**
	 * A builder interface to create an {@link EnchantmentFamily}. You can also create {@link EnchantmentFamily} by
	 * simply implementing the {@link EnchantmentFamily} interface (just like {@link DefaultEnchantmentFamily}).
	 */
	interface Builder {

		Builder bookItem(Supplier<EnchantedBookItem> bookItem);

		Builder prefix(Component prefix);

		Builder formattings(Function<AdvancedEnchantment, List<ChatFormatting>> formattings);

		Builder setInEnchantingTable(boolean inEnchantingTable);

		Builder familyCompatibilities(FilterList<EnchantmentFamily> familyCompatibilities);

		/**
		 * Builds the {@link EnchantmentFamily}.
		 * @return the newly created enchantment family
		 */
		EnchantmentFamily build();
	}
}
