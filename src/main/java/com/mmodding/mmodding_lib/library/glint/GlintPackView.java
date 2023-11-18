package com.mmodding.mmodding_lib.library.glint;

import com.mmodding.mmodding_lib.client.ClientCaches;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.library.utils.EnvironmentUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public interface GlintPackView {

	static GlintPackView of(ItemStack stack) {
		return GlintPackView.of(stack.getItem());
	}

	static GlintPackView of(Item item) {

		if (EnvironmentUtils.isClient()) {
			if (ClientCaches.GLINT_PACK_OVERRIDES.containsKey(item)) {
				return ClientCaches.GLINT_PACK_OVERRIDES.get(item);
			}
			else if (!EnvironmentUtils.isInSinglePlayer()) {
				if (ClientCaches.GLINT_PACKS.containsKey(item)) {
					return ClientCaches.GLINT_PACKS.get(item);
				}
			}
		}

		return item.getGlintPackView();
	}

	Identifier getIdentifier(ItemStack stack);

	@ClientOnly
	default GlintPack getGlintPack(ItemStack stack) {
		if (MModdingClientGlobalMaps.hasGlintPack(this.getIdentifier(stack))) {
			return MModdingClientGlobalMaps.getGlintPack(this.getIdentifier(stack));
		}
		else {
			throw new IllegalArgumentException("Glint Pack With Identifier " + this.getIdentifier(stack) + "Does Not Exist");
		}
	}
}
