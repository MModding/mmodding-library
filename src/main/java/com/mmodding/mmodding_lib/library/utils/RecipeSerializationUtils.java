package com.mmodding.mmodding_lib.library.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mmodding.mmodding_lib.mixin.accessors.ShapelessRecipeSerializerAccessor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class RecipeSerializationUtils {

	public static DefaultedList<Ingredient> getIngredients(JsonObject json, String key, int number) {
		DefaultedList<Ingredient> ingredients = ShapelessRecipeSerializerAccessor.getIngredients(JsonHelper.getArray(json, key));
		if (ingredients.isEmpty()) {
			throw new JsonParseException("No Ingredients For Recipe");
		}
		else if (ingredients.size() > number) {
			throw new JsonParseException("To Many Ingredients For Recipe");
		}
		else {
			return ingredients;
		}
	}

	public static DefaultedList<Ingredient> readIngredients(PacketByteBuf buf) {
		DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
        ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
		return ingredients;
	}

	public static void writeIngredients(PacketByteBuf buf, DefaultedList<Ingredient> ingredients) {
		buf.writeVarInt(ingredients.size());
		ingredients.forEach(ingredient -> ingredient.write(buf));
	}
}
