package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ApiStatus.Internal
public class RecipeHelperImpl implements RecipeHelper {

	private final Consumer<FinishedRecipe> exporter;
	private final ItemLike target;

	public RecipeHelperImpl(Consumer<FinishedRecipe> exporter, ItemLike target) {
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
		recipe.factory.save(this.exporter);
	}

	@Override
	public void shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		this.shapeless(1, category, consumer);
	}

	@Override
	public void shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.save(this.exporter);
	}

	@Override
	public void smelting(ItemLike item, RecipeCategory category, int experience, int time) {
		this.smelting(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void smelting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smelting(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), RecipeProvider.has(this.target))
			.save(this.exporter);
	}

	@Override
	public void blasting(ItemLike item, RecipeCategory category, int experience, int time) {
		this.blasting(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void blasting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.blasting(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), RecipeProvider.has(this.target))
			.save(this.exporter);
	}

	@Override
	public void smoking(ItemLike item, RecipeCategory category, int experience, int time) {
		this.smoking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void smoking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), RecipeProvider.has(this.target))
			.save(this.exporter);
	}

	@Override
	public void campfireCooking(ItemLike item, RecipeCategory category, int experience, int time) {
		this.campfireCooking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), RecipeProvider.has(this.target))
			.save(this.exporter);
	}

	@Override
	public void factory(RecipeBuilder factory) {
		factory.save(this.exporter);
	}
}
