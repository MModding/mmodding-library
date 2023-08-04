package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.collection.DefaultedList;

public class RecipeUtils {

	public static boolean ingredientMatches(Inventory inventory, Recipe<?> recipe, DefaultedList<Ingredient> ingredients, int outputCount) {
		RecipeMatcher matcher = new RecipeMatcher();
		int counter = 0;

		for(int index = 0; index < inventory.size(); index++) {
			ItemStack stack = inventory.getStack(index);
			if (!stack.isEmpty()) {
				counter++;
				matcher.addInput(stack, 1);
			}
		}

		return ingredients.size() == counter && matcher.match(recipe, null, outputCount);
	}
}
