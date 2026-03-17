package com.mmodding.library.datagen.impl.block;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

public class BlockModelProcessingImpl {

	public BlockStateSupplier singleton(RegistryKey<Block> blockKey, TexturedModel.Factory modelFactory) {
		return BlockStateModelGenerator.createSingletonBlockState(Registries.BLOCK.get(blockKey), modelFactory);
	}
}
