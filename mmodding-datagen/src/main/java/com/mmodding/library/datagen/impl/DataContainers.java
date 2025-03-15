package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.loot.block.BlockLootContainer;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public record DataContainers(List<LangContainer> langContainers, List<RecipeContainer> recipeContainers, List<BlockLootContainer> blockLootContainers) {

	public static DataContainers retrieveFrom(Class<?> clazz) {
		List<LangContainer> langContainers = new ArrayList<>();
		List<RecipeContainer> recipeContainers = new ArrayList<>();
		List<BlockLootContainer> blockLootContainers = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				try {
					Object object = field.get(null);
					if (object instanceof LangContainer container) {
						langContainers.add(container);
					}
					else if (object instanceof RecipeContainer container) {
						recipeContainers.add(container);
					}
					else if (object instanceof BlockLootContainer container) {
						blockLootContainers.add(container);
					}
				} catch (IllegalAccessException error) {
					throw new RuntimeException(error);
				}
			}
		}
		return new DataContainers(langContainers, recipeContainers, blockLootContainers);
	}
}
