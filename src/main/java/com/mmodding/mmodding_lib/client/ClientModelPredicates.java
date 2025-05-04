package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public class ClientModelPredicates {

	public static void register() {
		ModelPredicateProviderRegistry.register(
			new MModdingIdentifier("broken"),
			(stack, world, entity, seed) -> stack.getDamage() >= stack.getMaxDamage() ? 1.0f : 0.0f
		);
		ModelPredicateProviderRegistry.register(
			new MModdingIdentifier("throwing"),
			(stack, world, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0f : 0.0f
		);
	}
}
