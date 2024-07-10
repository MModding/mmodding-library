package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.Internal
public class InternalDataAccess {

	public InternalDataAccess() {
		throw new IllegalStateException("InternalDataAccess only contains static definitions");
	}

	@SuppressWarnings("unchecked")
	static LangProcessor<LangContainer> langProcessor(LangContainer container) {
		return ((LangProcessorAccess<LangContainer>) container).langProcessor();
	}

	static Consumer<RecipeHelper> recipeGenerator(RecipeContainer container) {
		return ((RecipeGeneratorAccess) container).recipeGenerator();
	}

	public interface LangProcessorAccess<T> {

		LangProcessor<T> langProcessor();
	}

	public interface RecipeGeneratorAccess {

		Consumer<RecipeHelper> recipeGenerator();
	}
}
