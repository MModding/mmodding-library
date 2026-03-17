package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DataContentType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.Map;
import java.util.function.Predicate;

public class DataManagerImpl implements DataManager {

	private final Map<Class<?>, ClassContentOperator> operators = new Object2ObjectOpenHashMap<>();

	public void loadElements(FabricDataGenerator.Pack pack) {
		this.operators.values().forEach(operator -> operator.operate(pack));
	}

	@Override
	public <T, P> DataManager data(Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, P processor) {
		ClassContentOperator operator = this.operators.computeIfAbsent(sourceClass, ClassContentOperator::new);
		operator.add(handler, type, ignored -> true, processor);
		return this;
	}

	@Override
	public <T, P> DataManager data(Class<?> sourceClass, Class<T> type, Predicate<T> filter, DataContentType<T, P> handler, P processor) {
		ClassContentOperator operator = this.operators.computeIfAbsent(sourceClass, ClassContentOperator::new);
		operator.add(handler, type, filter, processor);
		return this;
	}
}
