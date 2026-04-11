package com.mmodding.library.enchantment.api;

import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * An advanced version of the {@link Enchantment} class which provides the {@link EnchantmentFamily} attribute.
 */
public class AdvancedEnchantment extends Enchantment {

	private final EnchantmentFamily family;

	public AdvancedEnchantment(Rarity rarity, EnchantmentCategory enchantmentTarget, EquipmentSlot[] equipmentSlots) {
		this(EnchantmentFamily.DEFAULT, rarity, enchantmentTarget, equipmentSlots);
	}

	protected AdvancedEnchantment(EnchantmentFamily family, Rarity rarity, EnchantmentCategory enchantmentTarget, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentTarget, equipmentSlots);
		this.family = family;
	}

	public EnchantmentFamily getFamily() {
		return this.family;
	}

	@Override
	public Component getFullname(int level) {
		MutableComponent enchantment = Component.translatable(this.getDescriptionId());
		this.family.getFormattings(this).forEach(enchantment::withStyle);
		if (level != 1 || this.getMaxLevel() != 1) {
			enchantment.append(" ").append(Component.translatable("enchantment.level." + level));
		}
		return this.family.getPrefix().copy().append(enchantment);
	}
}
