package com.mmodding.library.block.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

@Mixin(BlockFamily.class)
public interface BlockFamilyAccessor {

	@Invoker("<init>")
	static BlockFamily mmodding$init(Block baseBlock) {
		throw new IllegalStateException();
	}

	@Accessor("variants")
	Map<BlockFamily.Variant, Block> mmodding$getVariants();

	@Accessor("generateStonecutterRecipe")
	void setGenerateStonecutterRecipe(boolean generateStonecutterRecipe);
}
