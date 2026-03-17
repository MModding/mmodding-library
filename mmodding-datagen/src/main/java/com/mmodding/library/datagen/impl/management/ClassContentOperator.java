package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.list.TriList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ClassContentOperator<T, P> {

	private final List<T> elements;
	private final TriList<DataContentType<T, P>, Predicate<T>, P> operations;

	public ClassContentOperator(Class<?> sourceClass) {
		this.elements = ClassContentOperator.extract(sourceClass);
		this.operations = TriList.create();
	}

	public void add(DataContentType<T, P> handler, Predicate<T> filter, P processor) {
		this.operations.add(handler, filter, processor);
	}

	public void operate(FabricDataGenerator.Pack pack) {
		Map<DataContentType<T, P>, BiList<P, List<T>>> collected = new Object2ObjectOpenHashMap<>();
		this.operations.forEach((handler, filter, processor) -> {
			BiList<P, List<T>> subs = collected.computeIfAbsent(handler, ignored -> BiList.create());
			subs.add(processor, this.elements.stream().filter(filter).toList());
		});
		collected.forEach((handler, computable) -> handler.handleContent(pack, computable));
	}

	public static <T> List<T> extract(Class<?> sourceClass) {
		return Arrays.stream(sourceClass.getDeclaredFields())
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.map(ClassContentOperator::<T>getObject)
			.toList();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getObject(Field field) {
		try {
			return (T) field.get(null);
		}
		catch (IllegalAccessException error) {
			throw new RuntimeException(error);
		}
	}
}
