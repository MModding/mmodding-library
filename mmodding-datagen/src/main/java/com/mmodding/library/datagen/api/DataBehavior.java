package com.mmodding.library.datagen.api;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;

public interface DataBehavior {

	/**
	 * Refers to the mod namespace.
 	 * @return the mod namespace
	 */
	String namespace();

	/**
	 * States the Default Lang Processor that the elements should use.
 	 * @return the Default Lang Processor
	 */
	LangProcessor<?> getLangProcessor();

	/**
	 * States the Default Block Loot Processor that blocks should use.
	 * @return the Default Block Loot Processor
	 */
	BlockLootProcessor getBlockLootProcessor();
}
