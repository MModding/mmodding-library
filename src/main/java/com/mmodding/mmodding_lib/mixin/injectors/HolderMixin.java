package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Holder.class)
public interface HolderMixin<T> extends TagRuntimeManagement<T> {

	@Override
	default boolean isIn(TagModifier<T> modifier) {
		throw new AssertionError();
	}
}
