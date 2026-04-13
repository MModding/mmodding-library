package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.CookingBookCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ApiStatus.Internal
public class RecipeHelperImpl implements RecipeHelper {

	private final RecipeProvider provider;
	private final RecipeOutput output;
	private final ItemLike target;

	public RecipeHelperImpl(RecipeProvider provider, RecipeOutput output, ItemLike target) {
		this.provider = provider;
		this.output = output;
		this.target = target;
	}

	@Override
	public void shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		this.shaped(1, category, consumer);
	}

	@Override
	public void shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.provider, this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.save(this.output);
	}

	@Override
	public void shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		this.shapeless(1, category, consumer);
	}

	@Override
	public void shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.provider, this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.save(this.output);
	}

	@Override
	public void smelting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		this.smelting(Ingredient.of(item), category, bookCategory, experience, time);
	}

	@Override
	public void smelting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		SimpleCookingRecipeBuilder.smelting(ingredient, category, bookCategory, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
	}

	@Override
	public void blasting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		this.blasting(Ingredient.of(item), category, bookCategory, experience, time);
	}

	@Override
	public void blasting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		SimpleCookingRecipeBuilder.blasting(ingredient, category, bookCategory, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
	}

	@Override
	public void smoking(ItemLike item, RecipeCategory category, int experience, int time) {
		this.smoking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void smoking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
	}

	@Override
	public void campfireCooking(ItemLike item, RecipeCategory category, int experience, int time) {
		this.campfireCooking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public void campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
	}

	@Override
	public void custom(Function<RecipeProvider, RecipeBuilder> factory) {
		factory.apply(this.provider).save(this.output);
	}
}
