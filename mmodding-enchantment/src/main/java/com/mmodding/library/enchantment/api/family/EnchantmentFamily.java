package com.mmodding.library.enchantment.api.family;

import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import com.mmodding.library.enchantment.impl.family.EnchantmentFamilyBuilderImpl;
import com.mmodding.library.java.api.list.filter.FilterList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * An {@link EnchantmentFamily} can be defined as an attribute of an {@link Enchantment} to classify them to.
 * Those families define generalized attributes applying to all their {@link Enchantment} objects such as an
 * enchanted book item, family compatibilities or visual details.
 */
public interface EnchantmentFamily {

	/**
	 * The registry to register families into.
	 */
	DynamicResourceKeyAttachment<Enchantment, EnchantmentFamily> REGISTRY = DynamicResourceKeyAttachment.create(Registries.ENCHANTMENT);

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
	 * The associated enchanted book item to the {@link EnchantmentFamily}.
	 * @return the enchanted book item
	 */
	Item getEnchantedBookItem();

	/**
	 * The prefix displayed before the name of every {@link Enchantment} of the {@link EnchantmentFamily}.
	 * @return the prefix
	 */
	Component getPrefix();

	/**
	 * A list of {@link ChatFormatting} applied to the name of an {@link Enchantment} of the {@link EnchantmentFamily}.
	 * @param enchantment the enchantment
	 * @return the formattings of the enchantment
	 */
	List<ChatFormatting> getFormattings(Holder<Enchantment> enchantment);

	/**
	 * Indicates if the {@link Enchantment} of the {@link EnchantmentFamily} can be obtained through enchanting tables.
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

		Builder bookItem(Supplier<Item> enchantedBookItem);

		Builder prefix(Component prefix);

		Builder formattings(Function<Holder<Enchantment>, List<ChatFormatting>> formattings);

		Builder setInEnchantingTable(boolean inEnchantingTable);

		Builder familyCompatibilities(FilterList<EnchantmentFamily> familyCompatibilities);

		/**
		 * Builds the {@link EnchantmentFamily}.
		 * @return the newly created enchantment family
		 */
		EnchantmentFamily build();
	}
}
