package com.mmodding.mmodding_lib.library.enchantments;

import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import com.mmodding.mmodding_lib.library.utils.TextUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEnchantment extends Enchantment implements EnchantmentRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final EnchantmentType type;

	public CustomEnchantment(Rarity rarity, EnchantmentTarget enchantmentTarget, EquipmentSlot... equipmentSlots) {
		this(EnchantmentType.DEFAULT, rarity, enchantmentTarget, equipmentSlots);
	}

	public CustomEnchantment(EnchantmentType type, Rarity rarity, EnchantmentTarget enchantmentTarget, EquipmentSlot... equipmentSlots) {
		super(rarity, enchantmentTarget, equipmentSlots);
		this.type = type;
	}

	public EnchantmentType getType() {
		return this.type;
	}

	@Override
	public Text getName(int level) {
		MutableText enchantment = Text.translatable(this.getTranslationKey());

		if (this.isCursed()) {
			enchantment.formatted(Formatting.RED);
		}

		if (level != 1 || this.getMaxLevel() != 1) {
			TextUtils.spaceBetween(enchantment, Text.translatable("enchantment.level." + level));
		}

		return this.type.getPrefix().isSpaced() ? TextUtils.spaceBetween(this.type.getPrefix().copy(), enchantment) : this.type.getPrefix().copy().append(enchantment);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		registered.set(true);
	}
}
