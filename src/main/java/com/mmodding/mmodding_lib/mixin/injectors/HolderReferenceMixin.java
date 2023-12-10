package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Reference.class)
public interface HolderReferenceMixin<T> extends HolderMixin<T> {

	@Shadow
	T value();

	@Override
	default boolean isIn(TagModifier<T> modifier) {
		return modifier.result(this.value(), this::isIn);
	}
}
