package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.CookingBookCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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
	public RecipeHelper shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		return this.shaped(1, category, consumer);
	}

	@Override
	public RecipeHelper shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.provider, this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		return this.shapeless(1, category, consumer);
	}

	@Override
	public RecipeHelper shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.provider, this.target, count, category);
		consumer.accept(recipe);
		recipe.factory.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper smelting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		return this.smelting(Ingredient.of(item), category, bookCategory, experience, time);
	}

	@Override
	public RecipeHelper smelting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		SimpleCookingRecipeBuilder.smelting(ingredient, category, bookCategory, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper blasting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		return this.blasting(Ingredient.of(item), category, bookCategory, experience, time);
	}

	@Override
	public RecipeHelper blasting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		SimpleCookingRecipeBuilder.blasting(ingredient, category, bookCategory, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper smoking(ItemLike item, RecipeCategory category, int experience, int time) {
		return this.smoking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public RecipeHelper smoking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper campfireCooking(ItemLike item, RecipeCategory category, int experience, int time) {
		return this.campfireCooking(Ingredient.of(item), category, experience, time);
	}

	@Override
	public RecipeHelper campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time)
			.unlockedBy(RecipeProvider.getHasName(this.target), this.provider.has(this.target))
			.save(this.output);
		return this;
	}

	@Override
	public RecipeHelper custom(BiFunction<RecipeProvider, ItemLike, RecipeBuilder> factory) {
		return this.provide(
			(provider, target) -> factory.apply(provider, target)
				.unlockedBy(RecipeProvider.getHasName(target), provider.has(target))
				.save(this.output)
		);
	}

	@Override
	public RecipeHelper provide(BiConsumer<RecipeProvider, ItemLike> consumer) {
		consumer.accept(this.provider, this.target);
		return this;
	}
}
