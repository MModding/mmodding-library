package com.mmodding.mmodding_lib.library.items.settings;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.items.settings.additional.AdditionalItemSetting;
import com.mmodding.mmodding_lib.library.items.settings.additional.AdditionalItemSettingImpl;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class AdvancedItemSettings extends FabricItemSettings {

	public static final AdditionalItemSetting<List<Formatting>> NAME_FORMATTINGS = AdditionalItemSetting.create(Collections::emptyList);
	public static final AdditionalItemSetting<Text[]> DESCRIPTION_LINES = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<Function<ItemStack, Boolean>> GLINT = AdditionalItemSetting.create(stack -> false);
	public static final AdditionalItemSetting<GlintPackView> GLINT_PACK = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<Boolean> EATABLE = AdditionalItemSetting.create(Boolean.FALSE);
	public static final AdditionalItemSetting<Boolean> DRINKABLE = AdditionalItemSetting.create(Boolean.FALSE);
	public static final AdditionalItemSetting<Predicate<ItemStack>> HAS_BROKEN_STATE = AdditionalItemSetting.create(stack -> false);
	public static final AdditionalItemSetting<ItemUse> ITEM_USE = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemFinishUsing> ITEM_FINISH_USING = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemUseOnBlock> ITEM_USE_ON_BLOCK = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemUseOnEntity> ITEM_USE_ON_ENTITY = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemDropped> ITEM_DROPPED = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemPostHit> ITEM_POST_HIT = AdditionalItemSetting.create(() -> null);
	public static final AdditionalItemSetting<ItemPostMine> ITEM_POST_MINE = AdditionalItemSetting.create(() -> null);

	public <T> AdvancedItemSettings additionalSetting(AdditionalItemSetting<T> setting, T value) {
		((AdditionalItemSettingImpl<T>) setting).append(this, value);
		return this;
	}

	public AdvancedItemSettings nameFormattings(Formatting... formattings) {
		return this.additionalSetting(NAME_FORMATTINGS, List.of(formattings));
	}

	public AdvancedItemSettings descriptionLines(Text... descriptionLines) {
		return this.additionalSetting(DESCRIPTION_LINES, descriptionLines);
	}

	public AdvancedItemSettings glint() {
		return this.glint(true);
	}

	public AdvancedItemSettings glint(boolean glint) {
		return this.glint(stack -> glint);
	}

	public AdvancedItemSettings glint(Function<ItemStack, Boolean> glint) {
		return this.additionalSetting(GLINT, glint);
	}

	public AdvancedItemSettings glintPack(GlintPackView view) {
		return this.additionalSetting(GLINT_PACK, view);
	}

	public AdvancedItemSettings eatable() {
		return this.additionalSetting(EATABLE, true);
	}

	public AdvancedItemSettings drinkable() {
		return this.additionalSetting(DRINKABLE, true);
	}

	public AdvancedItemSettings hasBrokenState() {
		return this.hasBrokenState(stack -> true);
	}

	public AdvancedItemSettings hasBrokenState(Predicate<ItemStack> hasBrokenState) {
		return this.additionalSetting(HAS_BROKEN_STATE, hasBrokenState);
	}

	public AdvancedItemSettings itemUse(ItemUse itemUseSetting) {
		return this.additionalSetting(ITEM_USE, itemUseSetting);
	}

	public AdvancedItemSettings itemFinishUsing(ItemFinishUsing itemFinishUsingSetting) {
		return this.additionalSetting(ITEM_FINISH_USING, itemFinishUsingSetting);
	}

	public AdvancedItemSettings itemUseOnBlock(ItemUseOnBlock itemUseOnBlock) {
		return this.additionalSetting(ITEM_USE_ON_BLOCK, itemUseOnBlock);
	}

	public AdvancedItemSettings itemUseOnEntity(ItemUseOnEntity itemUseOnEntity) {
		return this.additionalSetting(ITEM_USE_ON_ENTITY, itemUseOnEntity);
	}

	public AdvancedItemSettings itemDropped(ItemDropped itemDropped) {
		return this.additionalSetting(ITEM_DROPPED, itemDropped);
	}

	public AdvancedItemSettings itemPostHit(ItemPostHit itemPostHit) {
		return this.additionalSetting(ITEM_POST_HIT, itemPostHit);
	}

	public AdvancedItemSettings itemPostMine(ItemPostMine itemPostMine) {
		return this.additionalSetting(ITEM_POST_MINE, itemPostMine);
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
	public AdvancedItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
		super.equipmentSlot(equipmentSlotProvider);
		return this;
	}

	@Override
	public AdvancedItemSettings customDamage(CustomDamageHandler handler) {
		super.customDamage(handler);
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
