package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.handler.DataContentType;
import com.mmodding.library.datagen.api.management.processor.ContentProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManagerImpl implements DataManager {

	private final Map<DataContentType<?, ?>, BiList<List<?>, ContentProcessor<?, ?>>> data = new HashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void loadElements(AdvancedContainer mod, FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		this.data.forEach((handler, biList) -> handler.handleContent((BiList) biList, pack));
	}

	@Override
	public <I, O> DataManager data(Class<?> sourceClass, Class<I> type, DataContentType<I, O> handler, ContentProcessor<I, O> processor) {
		this.data.computeIfAbsent(handler, ignored -> BiList.create()).add(DataContentType.extractOfType(sourceClass, type), processor);
		return this;
	}
}
