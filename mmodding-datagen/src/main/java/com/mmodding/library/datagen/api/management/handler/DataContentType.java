package com.mmodding.library.datagen.api.management.handler;

import com.mmodding.library.datagen.api.management.processor.ContentProcessor;
import com.mmodding.library.datagen.impl.management.handler.ContentHandlerImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.List;

public interface DataContentType<I, O> {

	void handleContent(List<I> elements, ContentProcessor<I, O> processor, FabricDataGenerator.Pack pack);

	static <T> List<T> extractOfType(Class<?> providerClass, Class<? extends T> type) {
		return ContentHandlerImpl.extractOfType(providerClass, type);
	}
}
