package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface AbstractBlockAccessor {

	@Accessor
	AbstractBlock.Settings getSettings();

	@Accessor
	Material getMaterial();
}
