package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.loot.block.BlockLootContainer;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements RecipeContainer, BlockLootContainer, InternalDataAccess.RecipeGeneratorAccess, InternalDataAccess.BlockLootContainerAccess {

	@Unique
	public Consumer<RecipeHelper> recipeGenerator = null;

	@Unique
	public BlockLootProcessor blockLootProcessor = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ItemConvertible> T recipe(Consumer<RecipeHelper> helper) {
		this.recipeGenerator = helper;
		return (T) this;
	}

	@Override
	public Block loot(BlockLootProcessor processor) {
		this.blockLootProcessor = processor;
		return (Block) (Object) this;
	}

	@Override
	public Consumer<RecipeHelper> recipeGenerator() {
		return this.recipeGenerator;
	}

	@Override
	public BlockLootProcessor blockLootProcessor() {
		return this.blockLootProcessor;
	}
}
