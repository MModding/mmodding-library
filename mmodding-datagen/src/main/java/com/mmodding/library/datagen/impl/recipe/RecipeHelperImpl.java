package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.Internal
public class RecipeHelperImpl implements RecipeHelper {

	private final Consumer<RecipeJsonProvider> exporter;
	private final ItemConvertible target;

	public RecipeHelperImpl(Consumer<RecipeJsonProvider> exporter, ItemConvertible target) {
		this.exporter = exporter;
		this.target = target;
	}

	@Override
	public void shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		this.shaped(1, category, consumer);
	}

	@Override
	public void shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.offerTo(this.exporter);
	}

	@Override
	public void shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		this.shapeless(1, category, consumer);
	}

	@Override
	public void shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.offerTo(this.exporter);
	}

	@Override
	public void smelting(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.smelting(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void smelting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		CookingRecipeJsonBuilder.createSmelting(ingredient, category, this.target, experience, time)
			.criterion(RecipeProvider.hasItem(this.target), RecipeProvider.conditionsFromItem(this.target))
			.offerTo(this.exporter);
	}

	@Override
	public void blasting(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.blasting(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void blasting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		CookingRecipeJsonBuilder.createBlasting(ingredient, category, this.target, experience, time)
			.criterion(RecipeProvider.hasItem(this.target), RecipeProvider.conditionsFromItem(this.target))
			.offerTo(this.exporter);
	}

	@Override
	public void smoking(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.smoking(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void smoking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		CookingRecipeJsonBuilder.createSmoking(ingredient, category, this.target, experience, time)
			.criterion(RecipeProvider.hasItem(this.target), RecipeProvider.conditionsFromItem(this.target))
			.offerTo(this.exporter);
	}

	@Override
	public void campfireCooking(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.campfireCooking(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		CookingRecipeJsonBuilder.createSmoking(ingredient, category, this.target, experience, time)
			.criterion(RecipeProvider.hasItem(this.target), RecipeProvider.conditionsFromItem(this.target))
			.offerTo(this.exporter);
	}

	@Override
	public void factory(CraftingRecipeJsonBuilder factory) {
		factory.offerTo(this.exporter);
	}
}
