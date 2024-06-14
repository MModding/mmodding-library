package com.mmodding.library.datagen.api.lang;

public interface LangContainer {

	default <T> T lang(LangProcessor<T> processor) {
		throw new IllegalStateException();
	}
}
