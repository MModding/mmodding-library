package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class RecipeGeneratorImpl implements RecipeGenerator {

	private final RecipeProvider provider;
	private final RecipeOutput output;

	public RecipeGeneratorImpl(RecipeProvider provider, RecipeOutput output) {
		this.provider = provider;
		this.output = output;
	}

	@Override
	public RecipeHelper forItem(ItemLike item) {
		return new RecipeHelperImpl(this.provider, this.output, item);
	}

	@Override
	public HolderGetter<Item> getItemLookup() {
		return this.provider.items;
	}

	@Override
	public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key) {
		return this.provider.registries.lookupOrThrow(key);
	}
}
