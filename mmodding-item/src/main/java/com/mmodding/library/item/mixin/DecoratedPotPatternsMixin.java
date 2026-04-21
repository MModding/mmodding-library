package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.item.api.properties.CustomItemProperty;
import com.mmodding.library.item.api.properties.MModdingItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {

	@WrapMethod(method = "getPatternFromItem")
	private static @Nullable ResourceKey<DecoratedPotPattern> applyCustomPatterns(Item item, Operation<ResourceKey<DecoratedPotPattern>> original) {
		ResourceKey<DecoratedPotPattern> custom = CustomItemProperty.get(item, MModdingItemProperties.DECORATED_POT_PATTERN);
		return custom != null ? custom : original.call(item);
	}
}
