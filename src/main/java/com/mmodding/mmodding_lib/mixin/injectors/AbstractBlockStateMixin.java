package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin implements TagRuntimeManagement.BlockStateTagRuntimeManagement {

	@Shadow
	public abstract Block getBlock();

	@Shadow
	public abstract boolean isIn(TagKey<Block> tag);

	@Override
	public boolean isIn(TagModifier<Block> modifier) {
		return modifier.result(this.getBlock(), this::isIn);
	}
}
