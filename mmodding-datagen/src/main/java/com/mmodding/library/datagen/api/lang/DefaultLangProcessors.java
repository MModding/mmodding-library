package com.mmodding.library.datagen.api.lang;

import com.mmodding.library.datagen.api.management.processor.ContentProcessor;
import com.mmodding.library.datagen.impl.lang.DefaultLangProcessorsImpl;
import org.jetbrains.annotations.ApiStatus;

public class DefaultLangProcessors {

	public static <T> ContentProcessor<T, String> getClassic() {
		return new DefaultLangProcessorsImpl.ClassicLangProcessorImpl<>();
	}
}
