package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ApiStatus.Internal
public class RecipeHelperImpl implements RecipeHelper {

	private final List<Supplier<? extends RecipeJsonFactory>> factories;

	private final ItemConvertible target;

	public RecipeHelperImpl(ItemConvertible target) {
		this.factories = new ArrayList<>();
		this.target = target;
	}

	@Override
	public void shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		this.factories.add(() -> {
			ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.target, 1, category);
			consumer.accept(recipe);
			return recipe.factory;
		});
	}

	@Override
	public void shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer) {
		this.factories.add(() -> {
			ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.target, count, category);
			consumer.accept(recipe);
			return recipe.factory;
		});
	}

	@Override
	public void shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		this.factories.add(() -> {
			ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.target, 1, category);
			consumer.accept(recipe);
			return recipe.factory;
		});
	}

	@Override
	public void shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer) {
		this.factories.add(() -> {
			ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.target, count, category);
			consumer.accept(recipe);
			return recipe.factory;
		});
	}

	@Override
	public void smelting(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.smelting(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void smelting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		this.factories.add(
			() -> CookingRecipeJsonFactory.createSmelting(
				ingredient,
				category,
				this.target,
				experience,
				time
			).criterion(RecipesProvider.hasItem(this.target), RecipesProvider.conditionsFromItem(this.target))
		);
	}

	@Override
	public void blasting(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.blasting(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void blasting(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		this.factories.add(
			() -> CookingRecipeJsonFactory.createBlasting(
				ingredient,
				category,
				this.target,
				experience,
				time
			).criterion(RecipesProvider.hasItem(this.target), RecipesProvider.conditionsFromItem(this.target))
		);
	}

	@Override
	public void smoking(ItemConvertible item, RecipeCategory category, int experience, int time) {
		this.smoking(Ingredient.ofItems(item), category, experience, time);
	}

	@Override
	public void smoking(Ingredient ingredient, RecipeCategory category, int experience, int time) {
		this.factories.add(
			() -> CookingRecipeJsonFactory.createSmoking(
				ingredient,
				category,
				this.target,
				experience,
				time
			).criterion(RecipesProvider.hasItem(this.target), RecipesProvider.conditionsFromItem(this.target))
		);
	}

	@Override
	public void factory(Supplier<? extends RecipeJsonFactory> factory) {
		this.factories.add(factory);
	}

	public List<Supplier<? extends RecipeJsonFactory>> getFactories() {
		return this.factories;
	}
}
