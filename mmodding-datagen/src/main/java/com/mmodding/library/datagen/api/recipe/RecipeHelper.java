package com.mmodding.library.datagen.api.recipe;

import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ApiStatus.NonExtendable
public interface RecipeHelper {

	/**
	 * Creates a shaped recipe for the current item
	 * @param category the recipe category
	 * @param consumer the recipe options
	 */
	void shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer);

	/**
	 * Creates a shaped recipe for the current item.
	 * @param count the recipe output count
	 * @param category the recipe category
	 * @param consumer the recipe options
	 */
	void shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer);

	/**
	 * Creates a shapeless recipe for the current item.
	 * @param category the recipe category
	 * @param consumer the recipe options
	 */
	void shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer);

	/**
	 * Creates a shapeless recipe for the current item.
	 * @param count the recipe output count
	 * @param category the recipe category
	 * @param consumer the recipe options
	 */
	void shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer);

	void smelting(ItemLike item, RecipeCategory category, int experience, int time);

	void smelting(Ingredient ingredient, RecipeCategory category, int experience, int time);

	void blasting(ItemLike item, RecipeCategory category, int experience, int time);

	void blasting(Ingredient ingredient, RecipeCategory category, int experience, int time);

	void smoking(ItemLike item, RecipeCategory category, int experience, int time);

	void smoking(Ingredient ingredient, RecipeCategory category, int experience, int time);

	void campfireCooking(ItemLike item, RecipeCategory category, int experience, int time);

	void campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time);

	void factory(RecipeBuilder factory);

	@ApiStatus.NonExtendable
	interface ShapedRecipe {

		default void key(char key, ItemLike item) {
			this.key(key, Ingredient.of(item));
		}

		void key(char key, Ingredient ingredient);

		void pattern(String firstLine, String secondLine, String thirdLine);

		void pattern(String firstLine, String secondLine);
	}

	@ApiStatus.NonExtendable
	interface ShapelessRecipe {

		void with(ItemLike... items);

		void with(Ingredient... ingredients);
	}

	@ApiStatus.NonExtendable
	interface SmeltingRecipe {

		default void input(ItemLike item) {
			this.input(Ingredient.of(item));
		}

		void input(Ingredient ingredient);

		void time(int time);

		default void output(ItemLike item) {
			this.input(Ingredient.of(item));
		}

		void output(Ingredient ingredient);

		void experience(int experience);
	}
}
