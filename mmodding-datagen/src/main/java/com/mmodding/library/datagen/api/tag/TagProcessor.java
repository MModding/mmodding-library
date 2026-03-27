package com.mmodding.library.datagen.api.tag;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Function;

public interface TagProcessor<T> {

	/**
	 * Processes elements by appending them to preconfigured tags
	 * @param builderProvider a provider function to retrieve tag builders
	 * @param element the element to process
	 */
	void process(Function<TagKey<T>, FabricTagProvider<T>.FabricTagBuilder> builderProvider, T element);
}
