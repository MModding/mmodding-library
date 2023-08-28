package com.mmodding.mmodding_lib.library.potions;

import com.mmodding.mmodding_lib.library.base.MModdingBootStrapInitializer;
import com.mmodding.mmodding_lib.mixin.accessors.BrewingRecipeRegistryAccessor;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPotion extends Potion implements PotionRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomPotion(StatusEffectInstance... statusEffectInstances) {
		super(statusEffectInstances);
	}

	public CustomPotion(@Nullable String string, StatusEffectInstance... statusEffectInstances) {
		super(string, statusEffectInstances);
	}

	/**
	 * @apiNote Must be used in a BootStrap Entrypoint
	 * @param basePotion the potion at the start of the potion creation
	 * @param ingredient the ingredient needed to make the potion
	 * @see MModdingBootStrapInitializer
	 */
	public CustomPotion addBrewingRecipe(Potion basePotion, Item ingredient) {
		BrewingRecipeRegistryAccessor.registerPotionRecipe(basePotion, ingredient, this);
		return this;
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
