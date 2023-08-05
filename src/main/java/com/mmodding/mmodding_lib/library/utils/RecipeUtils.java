package com.mmodding.mmodding_lib.library.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.collection.DefaultedList;

public class RecipeUtils {

	public static boolean ingredientMatches(Inventory inventory, IntArrayList recipeSlots, DefaultedList<Ingredient> ingredients) {
		if (ingredients.size() == recipeSlots.size()) {
			for (Ingredient ingredient : ingredients) {
				boolean found = false;
				for (int index = 0; index < recipeSlots.size(); index++) {
					int slot = recipeSlots.getInt(index);
					if (ingredient.test(inventory.getStack(slot))) {
						recipeSlots.removeInt(index);
						found = true;
						break;
					}
				}
				if (!found) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean ingredientMatches(Inventory inventory, Recipe<?> recipe, DefaultedList<Ingredient> ingredients) {
		return RecipeUtils.ingredientMatches(inventory, recipe, ingredients, 1);
	}

	public static boolean ingredientMatches(Inventory inventory, Recipe<?> recipe, DefaultedList<Ingredient> ingredients, int multiplier) {
		RecipeMatcher matcher = new RecipeMatcher();
		int counter = 0;

		for(int index = 0; index < inventory.size(); index++) {
			ItemStack stack = inventory.getStack(index);
			if (!stack.isEmpty()) {
				counter++;
				matcher.addInput(stack, 1);
			}
		}

		boolean test = ingredients.size() == counter && matcher.match(recipe, null, multiplier);
		System.out.println(test);
		return test;
	}
}
