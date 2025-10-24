package com.mmodding.library.enchantment.api;

import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * An advanced version of the {@link Enchantment} class which provides the {@link EnchantmentFamily} attribute.
 */
public class AdvancedEnchantment extends Enchantment {

	private final EnchantmentFamily family;

	public AdvancedEnchantment(Rarity rarity, EnchantmentTarget enchantmentTarget, EquipmentSlot[] equipmentSlots) {
		this(EnchantmentFamily.DEFAULT, rarity, enchantmentTarget, equipmentSlots);
	}

	protected AdvancedEnchantment(EnchantmentFamily family, Rarity rarity, EnchantmentTarget enchantmentTarget, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentTarget, equipmentSlots);
		this.family = family;
	}

	public EnchantmentFamily getFamily() {
		return this.family;
	}

	@Override
	public Text getName(int level) {
		MutableText enchantment = Text.translatable(this.getTranslationKey());
		this.family.getFormattings(this).forEach(enchantment::formatted);
		if (level != 1 || this.getMaxLevel() != 1) {
			enchantment.append(" ").append(Text.translatable("enchantment.level." + level));
		}
		return this.family.getPrefix().copy().append(enchantment);
	}
}
