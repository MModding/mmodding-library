package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class ItemMixin implements RecipeContainer, InternalDataAccess.RecipeGeneratorAccess {

	@Unique
	public Consumer<RecipeHelper> recipeGenerator = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ItemConvertible> T recipe(Consumer<RecipeHelper> helper) {
		this.recipeGenerator = helper;
		return (T) this;
	}

	@Override
	public Consumer<RecipeHelper> recipeGenerator() {
		return this.recipeGenerator;
	}
}
