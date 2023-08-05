package com.mmodding.mmodding_lib.mixin.accessors;

import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(Ingredient.class)
public interface IngredientAccessor {

	@Invoker("ofEntries")
	static Ingredient ofEntries(Stream<? extends Ingredient.Entry> entries) {
		throw new AssertionError();
	}

    @Invoker("entryFromJson")
    static Ingredient.Entry entryFromJson(JsonObject json) {
        throw new AssertionError();
    }
}
