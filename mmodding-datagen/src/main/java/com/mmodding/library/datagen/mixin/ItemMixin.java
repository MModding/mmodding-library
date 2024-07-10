package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class ItemMixin implements LangContainer, RecipeContainer, InternalDataAccess.LangProcessorAccess<Item>, InternalDataAccess.RecipeGeneratorAccess {

	@Unique
	public LangProcessor<Item> langProcessor = null;

	@Unique
	public Consumer<RecipeHelper> recipeGenerator = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<Item>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<Item>> langRegistry() {
		return RegistryKeys.ITEM;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ItemConvertible> T recipe(Consumer<RecipeHelper> helper) {
		this.recipeGenerator = helper;
		return (T) this;
	}

	@Override
	public LangProcessor<Item> langProcessor() {
		return this.langProcessor;
	}

	@Override
	public Consumer<RecipeHelper> recipeGenerator() {
		return this.recipeGenerator;
	}
}
