package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.glint.GlintPackView;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ItemGlintPack {

	/**
	 * @apiNote To Get The Glint Pack View Used By The Client Check {@link GlintPackView#ofItem(Item)} And {@link GlintPackView#ofStack(ItemStack)}
	 * @return The Glint Pack View Of The Item
	 */
    @Nullable
    default GlintPackView getGlintPackView() {
        throw new AssertionError();
    }
}
