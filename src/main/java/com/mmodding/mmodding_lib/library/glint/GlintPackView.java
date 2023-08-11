package com.mmodding.mmodding_lib.library.glint;

import com.mmodding.mmodding_lib.client.ClientCaches;
import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.EnvironmentUtils;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class GlintPackView {

	private final Identifier identifier;

	public GlintPackView(Identifier identifier) {
		this.identifier = identifier;
	}

	public static GlintPackView ofStack(ItemStack stack) {

		if (EnvironmentUtils.isClient()) {
			if (ClientCaches.GLINT_PACK_OVERRIDES.containsKey(stack.getItem())) {
				return ClientCaches.GLINT_PACK_OVERRIDES.get(stack.getItem());
			}
			else if (!EnvironmentUtils.isInSinglePlayer()) {
				if (ClientCaches.GLINT_PACKS.containsKey(stack.getItem())) {
					return GlintPackView.of(ClientCaches.GLINT_PACKS.get(stack.getItem()));
				}
			}
		}

		return stack.getGlintPackView();
	}

	public static GlintPackView ofItem(Item item) {

		if (EnvironmentUtils.isClient()) {
			if (ClientCaches.GLINT_PACK_OVERRIDES.containsKey(item)) {
				return ClientCaches.GLINT_PACK_OVERRIDES.get(item);
			}
			else if (!EnvironmentUtils.isInSinglePlayer()) {
				if (ClientCaches.GLINT_PACKS.containsKey(item)) {
					return GlintPackView.of(ClientCaches.GLINT_PACKS.get(item));
				}
			}
		}

		return item.getGlintPackView();
	}

	@ClientOnly
	public static GlintPackView of(GlintPack glintPack) {
		return new FalseGlintPackView(glintPack);
	}

    @ClientOnly
    public GlintPack getGlintPack() {
        if (MModdingClientGlobalMaps.hasGlintPack(this.identifier)) {
            return MModdingClientGlobalMaps.getGlintPack(this.identifier);
        }
        else {
            throw new IllegalArgumentException("Glint Pack With Identifier " + this.identifier + "Does Not Exist");
        }
    }

	public Identifier getIdentifier() {
		return this.identifier;
	}

	@ClientOnly
	private static class FalseGlintPackView extends GlintPackView {

		private final GlintPack glintPack;

		public FalseGlintPackView(GlintPack glintPack) {
			super(new MModdingIdentifier("unused"));
			this.glintPack = glintPack;
		}

		@Override
		public GlintPack getGlintPack() {
			return this.glintPack;
		}
	}
}
