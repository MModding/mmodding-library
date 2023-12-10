package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Reference.class)
public abstract class HolderReferenceMixin<T> implements TagRuntimeManagement<T>, Self<Holder.Reference<T>> {

	@Shadow
	public abstract T value();

	@Override
	public boolean isIn(TagModifier<T> modifier) {
		return modifier.result(this.value(), this.getObject()::isIn);
	}
}
