package com.mmodding.mmodding_lib.library.enchantments.types;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantedBookItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.FilterList;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.component.LiteralComponent;
import net.minecraft.text.component.TextComponent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface EnchantmentType {

	EnchantmentType DEFAULT = new DefaultEnchantmentType();

	static EnchantmentType of(String qualifier, Prefix prefix) {
		return EnchantmentType.of(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
	}

	static EnchantmentType of(String qualifier, Prefix prefix, boolean inEnchantingTable) {
		return new TableExclusionEnchantmentType(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), inEnchantingTable);
	}

	static EnchantmentType of(String qualifier, Prefix prefix, boolean inEnchantingTable, FilterList<EnchantmentType> filter) {
		return new FilteredEnchantmentType(qualifier, prefix, new AdvancedItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), inEnchantingTable, filter);
	}

	static EnchantmentType of(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings) {
		return new SimpleEnchantmentType(qualifier, prefix, enchantedBookSettings);
	}

	static EnchantmentType of(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable) {
		return new TableExclusionEnchantmentType(qualifier, prefix, enchantedBookSettings, inEnchantingTable);
	}

	static EnchantmentType of(String qualifier, Prefix prefix, Item.Settings enchantedBookSettings, boolean inEnchantingTable, FilterList<EnchantmentType> filter) {
		return new FilteredEnchantmentType(qualifier, prefix, enchantedBookSettings, inEnchantingTable, filter);
	}

	String getQualifier();

	Prefix getPrefix();

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

	class Prefix extends MutableText {

		private final boolean spaced;

		private Prefix(TextComponent component, List<Text> siblings, Style style, boolean spaced) {
			super(component, siblings, style);
			this.spaced = spaced;
		}

		public static Prefix spaced(String text) {
			return new Prefix(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, true);
		}

		public static Prefix of(String text) {
			return new Prefix(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, false);
		}

		public MutableText asMutable() {
			return Text.empty().append(this);
		}

		public boolean isSpaced() {
			return this.spaced;
		}

		@Override
		public Prefix copyContentOnly() {
			return new Prefix(this.asComponent(), new ArrayList<>(), Style.EMPTY, this.isSpaced());
		}

		@Override
		public Prefix copy() {
			return new Prefix(this.asComponent(), this.getSiblings(), this.getStyle(), this.isSpaced());
		}

		@Override
		public Prefix setStyle(Style style) {
			super.setStyle(style);
			return this;
		}

		@Override
		public Prefix append(String text) {
			super.append(text);
			return this;
		}

		@Override
		public Prefix append(Text text) {
			super.append(text);
			return this;
		}

		@Override
		public Prefix styled(UnaryOperator<Style> styleUpdater) {
			super.styled(styleUpdater);
			return this;
		}

		@Override
		public Prefix fillStyle(Style styleOverride) {
			super.fillStyle(styleOverride);
			return this;
		}

		@Override
		public Prefix formatted(Formatting... formattings) {
			super.formatted(formattings);
			return this;
		}

		@Override
		public Prefix formatted(Formatting formatting) {
			super.formatted(formatting);
			return this;
		}
	}
}
