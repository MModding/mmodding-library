package com.mmodding.mmodding_lib.mixin.accessors;

import com.google.gson.JsonArray;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShapelessRecipe.Serializer.class)
public interface ShapelessRecipeSerializerAccessor {

	@Invoker("getIngredients")
	static DefaultedList<Ingredient> invokeGetIngredients(JsonArray jsonArray) {
		throw new AssertionError();
	}
}
