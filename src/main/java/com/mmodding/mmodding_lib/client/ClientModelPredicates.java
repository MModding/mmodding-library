package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class ClientModelPredicates {

	public static void register() {
		ModelPredicateProviderRegistry.register(
			new MModdingIdentifier("throwing"),
			(stack, world, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0f : 0.0f
		);
	}
}
