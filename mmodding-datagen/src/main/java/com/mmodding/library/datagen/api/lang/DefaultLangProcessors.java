package com.mmodding.library.datagen.api.lang;

import com.mmodding.library.datagen.impl.lang.DefaultLangProcessorsImpl;

public class DefaultLangProcessors {

	public static <T> TranslationProcessor<T> getClassic() {
		return new DefaultLangProcessorsImpl.ClassicLangProcessorImpl<>();
	}
}
