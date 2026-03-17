package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.model.block.BlockStateModelProcessor;
import com.mmodding.library.datagen.api.model.item.ItemModelProcessor;
import com.mmodding.library.datagen.impl.management.handler.BlockModelTypeImpl;
import com.mmodding.library.datagen.impl.management.handler.DataTranslationTypeImpl;
import com.mmodding.library.datagen.impl.management.handler.ItemModelTypeImpl;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class DefaultContentTypes {

	public static final DataContentType<Item, ItemModelProcessor> ITEM_MODELS = new ItemModelTypeImpl();

	public static final DataContentType<Block, BlockStateModelProcessor> BLOCK_MODELS = new BlockModelTypeImpl();

	public static <T> DataContentType<T, TranslationProcessor<T>> getTranslationHandler(RegistryKey<? extends Registry<T>> registry) {
		return new DataTranslationTypeImpl<>(registry);
	}
}
