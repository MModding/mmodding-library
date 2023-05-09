package com.mmodding.mmodding_lib.library.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CustomItemSettings extends QuiltItemSettings {

	public static final CustomItemSetting<Text[]> DESCRIPTION_LINES = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<Boolean> GLINT = CustomItemSetting.create(Boolean.FALSE);
	public static final CustomItemSetting<ItemUseSetting> ITEM_USE = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<ItemFinishUsingSetting> ITEM_FINISH_USING = CustomItemSetting.create(() -> null);
	public static final CustomItemSetting<Boolean> EATABLE = CustomItemSetting.create(Boolean.FALSE);
	public static final CustomItemSetting<Boolean> DRINKABLE = CustomItemSetting.create(Boolean.FALSE);

	public CustomItemSettings descriptionLines(Text... descriptionLines) {
		return this.customSetting(DESCRIPTION_LINES, descriptionLines);
	}

	public CustomItemSettings glint() {
		return this.customSetting(GLINT, true);
	}

	public CustomItemSettings itemUse(ItemUseSetting itemUseSetting) {
		return this.customSetting(ITEM_USE, itemUseSetting);
	}

	public CustomItemSettings itemFinishUsing(ItemFinishUsingSetting itemFinishUsingSetting) {
		return this.customSetting(ITEM_FINISH_USING, itemFinishUsingSetting);
	}

	public CustomItemSettings eatable() {
		return this.customSetting(EATABLE, true);
	}

	public CustomItemSettings drinkable() {
		return this.customSetting(DRINKABLE, true);
	}

	public CustomItemSettings food(int hunger, float saturation) {
		return this.food(hunger, saturation, false);
	}

	public CustomItemSettings food(int hunger, float saturation, boolean meat) {
		return this.food(hunger, saturation, meat, false);
	}

	public CustomItemSettings food(int hunger, float saturation, boolean meat, boolean alwaysEdible) {
		return this.food(hunger, saturation, meat, alwaysEdible, false);
	}

	public CustomItemSettings food(int hunger, float saturation, boolean meat, boolean alwaysEdible, boolean snack) {
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

	public interface ItemUseSetting {

		void apply(World world, PlayerEntity user, Hand hand);
	}

	public interface ItemFinishUsingSetting {

		void apply(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir);
	}
}
