package com.mmodding.mmodding_lib.library.glint;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SimpleGlintPackView implements GlintPackView {

	private final Identifier identifier;

	public SimpleGlintPackView(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public Identifier getGlintPack(ItemStack stack) {
		return this.identifier;
	}
}
