package com.mmodding.library.datagen.api.recipe;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.CookingBookCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
	 * @param unlockers the recipe unlockers
	 */
	RecipeHelper shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers);

	RecipeHelper shaped(String suffix, RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers);

	/**
	 * Creates a shaped recipe for the current item.
	 * @param count the recipe output count
	 * @param category the recipe category
	 * @param consumer the recipe options
	 * @param unlockers the recipe unlockers
	 */
	RecipeHelper shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers);

	RecipeHelper shaped(String suffix, int count, RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers);

	/**
	 * Creates a shapeless recipe for the current item.
	 * @param category the recipe category
	 * @param consumer the recipe options
	 * @param unlockers the recipe unlockers
	 */
	RecipeHelper shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers);

	RecipeHelper shapeless(String suffix, RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers);

	/**
	 * Creates a shapeless recipe for the current item.
	 * @param count the recipe output count
	 * @param category the recipe category
	 * @param consumer the recipe options
	 * @param unlockers the recipe unlockers
	 */
	RecipeHelper shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers);

	RecipeHelper shapeless(String suffix, int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers);

	RecipeHelper cutting(ItemLike item, RecipeCategory category, int count);

	RecipeHelper cutting(String suffix, ItemLike item, RecipeCategory category, int count);

	RecipeHelper cutting(Ingredient ingredient, RecipeCategory category, int count, ItemLike... unlockers);

	RecipeHelper cutting(String suffix, Ingredient ingredient, RecipeCategory category, int count, ItemLike... unlockers);

	RecipeHelper smelting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time);

	RecipeHelper smelting(String suffix, ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time);

	RecipeHelper smelting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers);

	RecipeHelper smelting(String suffix, Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers);

	RecipeHelper blasting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time);

	RecipeHelper blasting(String suffix, ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time);

	RecipeHelper blasting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers);

	RecipeHelper blasting(String suffix, Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers);

	RecipeHelper smoking(ItemLike item, RecipeCategory category, int experience, int time);

	RecipeHelper smoking(String suffix, ItemLike item, RecipeCategory category, int experience, int time);

	RecipeHelper smoking(Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers);

	RecipeHelper smoking(String suffix, Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers);

	RecipeHelper campfireCooking(ItemLike item, RecipeCategory category, int experience, int time);

	RecipeHelper campfireCooking(String suffix, ItemLike item, RecipeCategory category, int experience, int time);

	RecipeHelper campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers);

	RecipeHelper campfireCooking(String suffix, Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers);

	RecipeHelper custom(BiFunction<RecipeProvider, ItemLike, RecipeBuilder> factory);

	RecipeHelper custom(String suffix, BiFunction<RecipeProvider, ItemLike, RecipeBuilder> factory);

	RecipeHelper provide(BiConsumer<RecipeProvider, ItemLike> consumer);

	@ApiStatus.NonExtendable
	interface ShapedRecipe {

		ShapedRecipe key(char key, ItemLike item);

		ShapedRecipe key(char key, Ingredient ingredient);

		ShapedRecipe pattern(String firstLine, String secondLine, String thirdLine);

		ShapedRecipe pattern(String firstLine, String secondLine);

		ShapedRecipe pattern(String firstLine);
	}

	@ApiStatus.NonExtendable
	interface ShapelessRecipe {

		ShapelessRecipe with(ItemLike... items);

		ShapelessRecipe with(Ingredient... ingredients);
	}

	@ApiStatus.NonExtendable
	interface SmeltingRecipe {

		default SmeltingRecipe input(ItemLike item) {
			return this.input(Ingredient.of(item));
		}

		SmeltingRecipe input(Ingredient ingredient);

		SmeltingRecipe time(int time);

		default SmeltingRecipe output(ItemLike item) {
			return this.input(Ingredient.of(item));
		}

		SmeltingRecipe output(Ingredient ingredient);

		SmeltingRecipe experience(int experience);
	}
}
