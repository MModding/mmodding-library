package com.mmodding.mmodding_lib.mixin.accessors;

import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.class)
public interface IngredientAccessor {

    @Invoker("entryFromJson")
    static Ingredient.Entry entryFromJson(JsonObject json) {
        throw new AssertionError();
    }
}
