package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.management.handler.DataContentType;
import com.mmodding.library.datagen.api.management.processor.ContentProcessor;

public interface DataManager {

	<I, O> DataManager data(Class<?> sourceClass, Class<I> type, DataContentType<I, O> handler, ContentProcessor<I, O> processor);
}
