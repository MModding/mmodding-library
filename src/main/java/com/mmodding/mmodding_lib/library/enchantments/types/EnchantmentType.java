package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantedBookItem;
import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.FilterList;
import com.mmodding.mmodding_lib.library.texts.Prefix;
import com.mmodding.mmodding_lib.library.utils.TweakFunction;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;

public interface EnchantmentType {

	EnchantmentType DEFAULT = new DefaultEnchantmentType();

	static EnchantmentType of(String qualifier) {
		return EnchantmentType.builder(qualifier).build();
	}

	static Builder builder(String qualifier) {
		return new Builder(qualifier);
	}

	String getQualifier();

	EnchantedBookItem getEnchantedBook();

	Prefix getPrefix();

	List<Formatting> getFormattings(CustomEnchantment enchantment);

	boolean isInEnchantingTable();

	FilterList<EnchantmentType> getTypeCompatibilities();

	default void register(Identifier identifier) {
		if (this.getEnchantedBook() instanceof CustomEnchantedBookItem item) {
			item.register(identifier);
		}
	}

	class Builder {

		private final String qualifier;

		private AdvancedItemSettings enchantedBookItemSettings = new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON);
		private Prefix prefix = Prefix.empty();
		private Function<CustomEnchantment, List<Formatting>> formattings = enchantment -> List.of(enchantment.isCursed() ? Formatting.RED : Formatting.GRAY);
		private boolean inEnchantingTable = true;
		private FilterList<EnchantmentType> typeCompatibilities = FilterList.always();

		private Builder(String qualifier) {
			this.qualifier = qualifier;
		}

		public Builder enchantedBookItemSettings(TweakFunction<AdvancedItemSettings> tweak) {
			this.enchantedBookItemSettings = tweak.apply(this.enchantedBookItemSettings);
			return this;
		}

		public Builder prefix(Prefix prefix) {
			this.prefix = prefix;
			return this;
		}

		public Builder formattings(Function<CustomEnchantment, List<Formatting>> formattings) {
			this.formattings = formattings;
			return this;
		}

		public Builder inEnchantingTable(boolean inEnchantingTable) {
			this.inEnchantingTable = inEnchantingTable;
			return this;
		}

		public Builder typeCompatibilities(FilterList<EnchantmentType> typeCompatibilities) {
			this.typeCompatibilities = typeCompatibilities;
			return this;
		}

		public EnchantmentType build() {
			return new Impl(this.qualifier, this.enchantedBookItemSettings, this.prefix, this.formattings, this.inEnchantingTable, this.typeCompatibilities);
		}

		@ApiStatus.Internal
		private static class Impl implements EnchantmentType {

			private final String qualifier;
			private final CustomEnchantedBookItem enchantedBook;
			private final Prefix prefix;
			private final Function<CustomEnchantment, List<Formatting>> formattings;
			private final boolean inEnchantingTable;
			private final FilterList<EnchantmentType> typeCompatibilities;

			private Impl(String qualifier, AdvancedItemSettings enchantedBookItemSettings, Prefix prefix, Function<CustomEnchantment, List<Formatting>> formattings, boolean inEnchantingTable, FilterList<EnchantmentType> typeCompatibilities) {
				this.qualifier = qualifier;
				this.enchantedBook = new CustomEnchantedBookItem(this, enchantedBookItemSettings);
				this.prefix = prefix;
				this.formattings = formattings;
				this.inEnchantingTable = inEnchantingTable;
				this.typeCompatibilities = typeCompatibilities;
			}

			@Override
			public String getQualifier() {
				return this.qualifier;
			}

			@Override
			public EnchantedBookItem getEnchantedBook() {
				return this.enchantedBook;
			}

			@Override
			public Prefix getPrefix() {
				return this.prefix;
			}

			@Override
			public List<Formatting> getFormattings(CustomEnchantment enchantment) {
				return this.formattings.apply(enchantment);
			}

			@Override
			public boolean isInEnchantingTable() {
				return this.inEnchantingTable;
			}

			@Override
			public FilterList<EnchantmentType> getTypeCompatibilities() {
				return this.typeCompatibilities;
			}
		}
	}
}
