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
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class RecipeHelperImpl implements RecipeHelper {

	private final RecipeProvider provider;
	private final RecipeOutput output;
	private final ItemLike target;
	private final @Nullable String suffix;

	public RecipeHelperImpl(RecipeProvider provider, RecipeOutput output, ItemLike target, @Nullable String suffix) {
		this.provider = provider;
		this.output = output;
		this.target = target;
		this.suffix = suffix;
	}

	private RecipeHelper save(RecipeBuilder builder) {
		if (this.suffix != null) {
			builder.save(this.output, builder.defaultId().mapIdentifier(id -> id.withPath(s -> s + this.suffix)));
		}
		else {
			builder.save(this.output);
		}
		return this;
	}

	@Override
	public RecipeHelper shaped(RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers) {
		return this.shaped(1, category, consumer);
	}

	@Override
	public RecipeHelper shaped(int count, RecipeCategory category, Consumer<ShapedRecipe> consumer, ItemLike... unlockers) {
		ShapedRecipeImpl recipe = new ShapedRecipeImpl(this.provider, this.target, count, category);
		consumer.accept(recipe);
		return this.save(recipe.factory);
	}

	@Override
	public RecipeHelper shapeless(RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers) {
		return this.shapeless(1, category, consumer, unlockers);
	}

	@Override
	public RecipeHelper shapeless(int count, RecipeCategory category, Consumer<ShapelessRecipe> consumer, ItemLike... unlockers) {
		ShapelessRecipeImpl recipe = new ShapelessRecipeImpl(this.provider, this.target, count, category, unlockers);
		consumer.accept(recipe);
		return this.save(recipe.factory);
	}

	@Override
	public RecipeHelper cutting(ItemLike item, RecipeCategory category, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(item), category, this.target, count)
			.unlockedBy(RecipeProvider.getHasName(item), this.provider.has(item))
			.save(this.output, RecipeProvider.getConversionRecipeName(this.target, item) + "_stonecutting");
		return this;
	}

	@Override
	public RecipeHelper cutting(Ingredient ingredient, RecipeCategory category, int count, ItemLike... unlockers) {
		RecipeBuilder builder = SingleItemRecipeBuilder.stonecutting(ingredient, category, this.target, count);
		for (ItemLike unlocker : unlockers) {
			builder.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
		return this.save(builder);
	}

	@Override
	public RecipeHelper smelting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		return this.smelting(Ingredient.of(item), category, bookCategory, experience, time, item);
	}

	@Override
	public RecipeHelper smelting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers) {
		RecipeBuilder builder = SimpleCookingRecipeBuilder.smelting(ingredient, category, bookCategory, this.target, experience, time);
		for (ItemLike unlocker : unlockers) {
			builder.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
		return this.save(builder);
	}

	@Override
	public RecipeHelper blasting(ItemLike item, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time) {
		return this.blasting(Ingredient.of(item), category, bookCategory, experience, time, item);
	}

	@Override
	public RecipeHelper blasting(Ingredient ingredient, RecipeCategory category, CookingBookCategory bookCategory, int experience, int time, ItemLike... unlockers) {
		RecipeBuilder builder = SimpleCookingRecipeBuilder.blasting(ingredient, category, bookCategory, this.target, experience, time);
		for (ItemLike unlocker : unlockers) {
			builder.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
		return this.save(builder);
	}

	@Override
	public RecipeHelper smoking(ItemLike item, RecipeCategory category, int experience, int time) {
		return this.smoking(Ingredient.of(item), category, experience, time, item);
	}

	@Override
	public RecipeHelper smoking(Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers) {
		RecipeBuilder builder = SimpleCookingRecipeBuilder.smoking(ingredient, category, this.target, experience, time);
		for (ItemLike unlocker : unlockers) {
			builder.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
		return this.save(builder);
	}

	@Override
	public RecipeHelper campfireCooking(ItemLike item, RecipeCategory category, int experience, int time) {
		return this.campfireCooking(Ingredient.of(item), category, experience, time, item);
	}

	@Override
	public RecipeHelper campfireCooking(Ingredient ingredient, RecipeCategory category, int experience, int time, ItemLike... unlockers) {
		RecipeBuilder builder = SimpleCookingRecipeBuilder.campfireCooking(ingredient, category, this.target, experience, time);
		for (ItemLike unlocker : unlockers) {
			builder.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
		return this.save(builder);
	}

	@Override
	public RecipeHelper custom(BiFunction<RecipeProvider, ItemLike, RecipeBuilder> factory) {
		return this.provide(
			(provider, target) -> this.save(factory.apply(provider, target)
				.unlockedBy(RecipeProvider.getHasName(target), provider.has(target)))
		);
	}

	@Override
	public RecipeHelper provide(BiConsumer<RecipeProvider, ItemLike> consumer) {
		consumer.accept(this.provider, this.target);
		return this;
	}
}
