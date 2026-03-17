package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.container.Pair;
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
import java.util.stream.Stream;

public class ClassContentOperator {

	private final List<?> elements;
	// list of (pair of (handler, type), filter, processor)
	private final TriList<Pair<DataContentType<?, ?>, Class<?>>, Predicate<?>, Object> operations;

	public ClassContentOperator(Class<?> sourceClass) {
		this.elements = ClassContentOperator.extract(sourceClass);
		this.operations = TriList.create();
	}

	public <T, P> void add(DataContentType<T, P> handler, Class<T> type, Predicate<T> filter, P processor) {
		this.operations.add(Pair.create(handler, type), filter, processor);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void operate(FabricDataGenerator.Pack pack) {
		// dynamic retention of resolved elements of a type
		Map<Class<?>, List<?>> collected = new Object2ObjectOpenHashMap<>();
		Map<DataContentType<?, Object>, BiList<Object, List<?>>> tasks = new Object2ObjectOpenHashMap<>();
		this.operations.forEach((pair, filter, processor) -> {
			if (!collected.containsKey(pair.second())) {
				List<?> targets = this.elements.stream()
					.filter(
						element -> pair.second().isAssignableFrom(element.getClass()) ||
							(DataContentResolverImpl.REGISTRY.containsKey(element.getClass())
							&& pair.second().isAssignableFrom(DataContentResolverImpl.REGISTRY.getFirstValue(element.getClass())))
					)
					.flatMap(
						element -> pair.second().isAssignableFrom(element.getClass())
							? Stream.of(element)
							: ((DataContentResolver<Object, ?>) DataContentResolverImpl.REGISTRY.getSecondValue(element.getClass())).resolve(element).stream()
					)
					.toList();
				collected.put(pair.second(), targets);
			}
			List<?> typeCollected = collected.get(pair.second());
			List<?> filtered = typeCollected.stream().filter((Predicate<Object>) filter).toList();
			tasks.computeIfAbsent((DataContentType<?, Object>) pair.first(), ignored -> BiList.create()).add(processor, filtered);
		});
		tasks.forEach((handler, contentToProcess) -> handler.handleContent(pack, (BiList) contentToProcess));
	}

	public static List<?> extract(Class<?> sourceClass) {
		return Arrays.stream(sourceClass.getDeclaredFields())
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.map(ClassContentOperator::getObject)
			.toList();
	}

	private static Object getObject(Field field) {
		try {
			return field.get(null);
		}
		catch (IllegalAccessException error) {
			throw new RuntimeException(error);
		}
	}
}
