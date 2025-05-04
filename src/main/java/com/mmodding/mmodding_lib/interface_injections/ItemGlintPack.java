package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

@ClassExtension(Item.class)
public interface ItemGlintPack {

	/**
	 * @apiNote To Get The Glint Pack View Used By The Client Check {@link GlintPackView#of(Item)}
	 * @return The Glint Pack View Of The Item
	 */
    @Nullable
    default GlintPackView getGlintPackView() {
		return AdvancedItemSettings.GLINT_PACK.get((Item) this);
    }
}
