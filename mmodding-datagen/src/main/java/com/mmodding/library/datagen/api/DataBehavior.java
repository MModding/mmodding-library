package com.mmodding.library.datagen.api;

import com.mmodding.library.datagen.api.lang.LangProcessor;

public interface DataBehavior {

	/**
	 * States the mod namespace
 	 * @return the mod namespace
	 */
	String namespace();

	/**
	 * States the Default Lang Processor that the elements should use
 	 * @return the Default Lang Processor
	 */
	LangProcessor<?> getLangProcessor();
}
