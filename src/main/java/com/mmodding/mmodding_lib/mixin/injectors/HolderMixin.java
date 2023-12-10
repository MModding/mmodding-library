package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Direct.class)
public interface HolderMixin<T> extends TagRuntimeManagement<T> {

	@Shadow
	boolean isIn(TagKey<T> tag);
}
