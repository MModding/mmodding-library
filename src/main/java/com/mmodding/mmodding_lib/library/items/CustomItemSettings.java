package com.mmodding.mmodding_lib.library.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.CustomDamageHandler;
import org.quiltmc.qsl.item.setting.api.CustomItemSetting;
import org.quiltmc.qsl.item.setting.api.EquipmentSlotProvider;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class CustomItemSettings extends QuiltItemSettings {

	public static final CustomItemSetting<Text[]> DESCRIPTION_LINES = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<Boolean> GLINT = CustomItemSetting.create(() -> false);

	public CustomItemSettings descriptionLines(Text... descriptionLines) {
		return this.customSetting(DESCRIPTION_LINES, descriptionLines);
	}

	public CustomItemSettings glint(boolean glint) {
		return this.customSetting(GLINT, glint);
	}

	@Override
	public CustomItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
		super.equipmentSlot(equipmentSlotProvider);
		return this;
	}

	@Override
	public CustomItemSettings equipmentSlot(EquipmentSlot equipmentSlot) {
		super.equipmentSlot(equipmentSlot);
		return this;
	}

	@Override
	public CustomItemSettings customDamage(CustomDamageHandler handler) {
		super.customDamage(handler);
		return this;
	}

	@Override
	public <T> CustomItemSettings customSetting(CustomItemSetting<T> setting, T value) {
		super.customSetting(setting, value);
		return this;
	}

	@Override
	public CustomItemSettings food(FoodComponent foodComponent) {
		super.food(foodComponent);
		return this;
	}

	@Override
	public CustomItemSettings maxCount(int maxCount) {
		super.maxCount(maxCount);
		return this;
	}

	@Override
	public CustomItemSettings maxDamageIfAbsent(int maxDamage) {
		super.maxDamageIfAbsent(maxDamage);
		return this;
	}

	@Override
	public CustomItemSettings maxDamage(int maxDamage) {
		super.maxDamage(maxDamage);
		return this;
	}

	@Override
	public CustomItemSettings recipeRemainder(Item recipeRemainder) {
		super.recipeRemainder(recipeRemainder);
		return this;
	}

	@Override
	public CustomItemSettings group(ItemGroup group) {
		super.group(group);
		return this;
	}

	@Override
	public CustomItemSettings rarity(Rarity rarity) {
		super.rarity(rarity);
		return this;
	}

	@Override
	public CustomItemSettings fireproof() {
		super.fireproof();
		return this;
	}
}
