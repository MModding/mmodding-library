package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.utils.FilterList;
import com.mmodding.mmodding_lib.library.texts.Prefix;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public interface EnchantmentType {

	EnchantmentType DEFAULT = new DefaultEnchantmentType();

	static EnchantmentType of(String qualifier) {
		return EnchantmentType.builder(qualifier).build();
	}

	static Builder builder(String qualifier) {
		return new Builder(qualifier);
	}

	String getQualifier();

	EnchantedBookItem getBookItem();

	Prefix getPrefix();

	List<Formatting> getFormattings(CustomEnchantment enchantment);

	boolean canBeObtainedThroughEnchantingTable();

	FilterList<EnchantmentType> getTypeCompatibilities();

	class Builder {

		private final String qualifier;

		private Supplier<EnchantedBookItem> bookItem = () -> (EnchantedBookItem) Items.ENCHANTED_BOOK;
		private Prefix prefix = Prefix.empty();
		private Function<CustomEnchantment, List<Formatting>> formattings = enchantment -> List.of(enchantment.isCursed() ? Formatting.RED : Formatting.GRAY);
		private boolean inEnchantingTable = true;
		private FilterList<EnchantmentType> typeCompatibilities = FilterList.always();

		private Builder(String qualifier) {
			this.qualifier = qualifier;
		}

		public Builder bookItem(Supplier<EnchantedBookItem> bookItem) {
			this.bookItem = bookItem;
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
			return new Impl(this.qualifier, this.bookItem, this.prefix, this.formattings, this.inEnchantingTable, this.typeCompatibilities);
		}

		@ApiStatus.Internal
		@SuppressWarnings("ClassCanBeRecord")
		private static class Impl implements EnchantmentType {

			private final String qualifier;
			private final Supplier<EnchantedBookItem> bookItem;
			private final Prefix prefix;
			private final Function<CustomEnchantment, List<Formatting>> formattings;
			private final boolean inEnchantingTable;
			private final FilterList<EnchantmentType> typeCompatibilities;

			private Impl(String qualifier, Supplier<EnchantedBookItem> bookItem, Prefix prefix, Function<CustomEnchantment, List<Formatting>> formattings, boolean inEnchantingTable, FilterList<EnchantmentType> typeCompatibilities) {
				this.qualifier = qualifier;
				this.bookItem = bookItem;
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
			public EnchantedBookItem getBookItem() {
				return this.bookItem.get();
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
			public boolean canBeObtainedThroughEnchantingTable() {
				return this.inEnchantingTable;
			}

			@Override
			public FilterList<EnchantmentType> getTypeCompatibilities() {
				return this.typeCompatibilities;
			}
		}
	}
}
