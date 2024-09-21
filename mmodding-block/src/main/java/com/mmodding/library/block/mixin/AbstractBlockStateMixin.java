package com.mmodding.library.block.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

	@Shadow
	public abstract Block getBlock();
}
