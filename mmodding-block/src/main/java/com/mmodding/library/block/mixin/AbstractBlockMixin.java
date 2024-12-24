package com.mmodding.library.block.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

	@Shadow
	@Final
	protected Material material;
}
