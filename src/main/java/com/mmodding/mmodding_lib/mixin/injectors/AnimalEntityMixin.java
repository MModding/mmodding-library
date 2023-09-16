package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntityMixin {

	@Shadow
	public abstract boolean isBreedingItem(ItemStack stack);

	@Shadow
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		throw new AssertionError();
	}
}
