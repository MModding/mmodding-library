package com.mmodding.mmodding_lib.library.glint;

import com.mmodding.mmodding_lib.library.utils.EnvRestriction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@EnvRestriction(EnvRestriction.Side.SERVER_COMMON)
public interface GlintPackView {

	static GlintPackView of(ItemStack stack) {
		return GlintPackView.of(stack.getItem());
	}

	static GlintPackView of(Item item) {
		return item.getGlintPackView();
	}

	Identifier getGlintPack(ItemStack stack);
}
