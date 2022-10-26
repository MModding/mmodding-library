package com.mmodding.mmodding_lib.library.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

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
	public boolean isNotRegistered() {
		return !registered.get();
	}

	@Override
	public void setRegistered() {
		registered.set(true);
	}
}
