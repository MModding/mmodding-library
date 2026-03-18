package com.mmodding.library.block.mixin;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(BlockFamily.class)
public interface BlockFamilyAccessor {

	@Invoker("<init>")
	static BlockFamily mmodding$init(Block baseBlock) {
		throw new IllegalStateException();
	}

	@Accessor("variants")
	Map<BlockFamily.Variant, Block> mmodding$getVariants();
}
