package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Holder.Direct.class)
public interface HolderDirectMixin<T> extends HolderMixin<T> {

	@Override
	default boolean isIn(TagModifier<T> modifier) {
		return false;
	}
}
