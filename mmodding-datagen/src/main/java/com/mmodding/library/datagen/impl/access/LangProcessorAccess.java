package com.mmodding.library.datagen.impl.access;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface LangProcessorAccess<T> {

	LangProcessor<T> processor();
}
