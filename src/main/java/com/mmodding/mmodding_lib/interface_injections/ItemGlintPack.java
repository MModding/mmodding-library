package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface({Item.class, ItemStack.class})
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
