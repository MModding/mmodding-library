package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.*;

public class AdvancedItemSettings extends QuiltItemSettings {

	public static final CustomItemSetting<Text[]> DESCRIPTION_LINES = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<Boolean> GLINT = CustomItemSetting.create(Boolean.FALSE);
	public static final CustomItemSetting<Boolean> EATABLE = CustomItemSetting.create(Boolean.FALSE);
	public static final CustomItemSetting<Boolean> DRINKABLE = CustomItemSetting.create(Boolean.FALSE);
	public static final CustomItemSetting<ItemUse> ITEM_USE = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemFinishUsing> ITEM_FINISH_USING = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemUseOnBlock> ITEM_USE_ON_BLOCK = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemUseOnEntity> ITEM_USE_ON_ENTITY = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemDropped> ITEM_DROPPED = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemPostHit> ITEM_POST_HIT = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemPostMine> ITEM_POST_MINE = CustomItemSetting.create(() -> null);

	public AdvancedItemSettings descriptionLines(Text... descriptionLines) {
		return this.customSetting(DESCRIPTION_LINES, descriptionLines);
	}

	public AdvancedItemSettings glint() {
		return this.customSetting(GLINT, true);
	}

	public AdvancedItemSettings eatable() {
		return this.customSetting(EATABLE, true);
	}

	public AdvancedItemSettings drinkable() {
		return this.customSetting(DRINKABLE, true);
	}

	public AdvancedItemSettings itemUse(ItemUse itemUseSetting) {
		return this.customSetting(ITEM_USE, itemUseSetting);
	}

	public AdvancedItemSettings itemFinishUsing(ItemFinishUsing itemFinishUsingSetting) {
		return this.customSetting(ITEM_FINISH_USING, itemFinishUsingSetting);
	}

	public AdvancedItemSettings itemUseOnBlock(ItemUseOnBlock itemUseOnBlock) {
		return this.customSetting(ITEM_USE_ON_BLOCK, itemUseOnBlock);
	}

	public AdvancedItemSettings itemUseOnEntity(ItemUseOnEntity itemUseOnEntity) {
		return this.customSetting(ITEM_USE_ON_ENTITY, itemUseOnEntity);
	}

	public AdvancedItemSettings itemDropped(ItemDropped itemDropped) {
		return this.customSetting(ITEM_DROPPED, itemDropped);
	}

	public AdvancedItemSettings itemPostHit(ItemPostHit itemPostHit) {
		return this.customSetting(ITEM_POST_HIT, itemPostHit);
	}

	public AdvancedItemSettings itemPostMine(ItemPostMine itemPostMine) {
		return this.customSetting(ITEM_POST_MINE, itemPostMine);
	}

	public AdvancedItemSettings food(int hunger, float saturation) {
		return this.food(hunger, saturation, false);
	}

	public AdvancedItemSettings food(int hunger, float saturation, boolean meat) {
		return this.food(hunger, saturation, meat, false);
	}

	public AdvancedItemSettings food(int hunger, float saturation, boolean meat, boolean alwaysEdible) {
		return this.food(hunger, saturation, meat, alwaysEdible, false);
	}

	public AdvancedItemSettings food(int hunger, float saturation, boolean meat, boolean alwaysEdible, boolean snack) {
		FoodComponent.Builder builder = new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);
		if (meat) builder.meat();
		if (alwaysEdible) builder.alwaysEdible();
		if (snack) builder.snack();
		return this.food(builder.build());
	}

	@Override
	public QuiltItemSettings recipeRemainder(RecipeRemainderProvider provider) {
		super.recipeRemainder(provider);
		return this;
	}

	@Override
	public QuiltItemSettings recipeDamageRemainder() {
		super.recipeDamageRemainder();
		return this;
	}

	@Override
	public QuiltItemSettings recipeSelfRemainder() {
		super.recipeSelfRemainder();
		return this;
	}

	@Override
	public QuiltItemSettings recipeDamageRemainder(int by) {
		super.recipeDamageRemainder(by);
		return this;
	}

	@Override
	public AdvancedItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
		super.equipmentSlot(equipmentSlotProvider);
		return this;
	}

	@Override
	public AdvancedItemSettings equipmentSlot(EquipmentSlot equipmentSlot) {
		super.equipmentSlot(equipmentSlot);
		return this;
	}

	@Override
	public AdvancedItemSettings customDamage(CustomDamageHandler handler) {
		super.customDamage(handler);
		return this;
	}

	@Override
	public <T> AdvancedItemSettings customSetting(CustomItemSetting<T> setting, T value) {
		super.customSetting(setting, value);
		return this;
	}

	@Override
	public AdvancedItemSettings food(FoodComponent foodComponent) {
		super.food(foodComponent);
		return this;
	}

	@Override
	public AdvancedItemSettings maxCount(int maxCount) {
		super.maxCount(maxCount);
		return this;
	}

	@Override
	public AdvancedItemSettings maxDamageIfAbsent(int maxDamage) {
		super.maxDamageIfAbsent(maxDamage);
		return this;
	}

	@Override
	public AdvancedItemSettings maxDamage(int maxDamage) {
		super.maxDamage(maxDamage);
		return this;
	}

	@Override
	public AdvancedItemSettings recipeRemainder(Item recipeRemainder) {
		super.recipeRemainder(recipeRemainder);
		return this;
	}

	@Override
	public AdvancedItemSettings group(ItemGroup group) {
		super.group(group);
		return this;
	}

	@Override
	public AdvancedItemSettings rarity(Rarity rarity) {
		super.rarity(rarity);
		return this;
	}

	@Override
	public AdvancedItemSettings fireproof() {
		super.fireproof();
		return this;
	}
}
