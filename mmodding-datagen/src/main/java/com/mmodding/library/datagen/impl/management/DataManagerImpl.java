package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.list.TriList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataManagerImpl implements DataManager {

	// dynamic approach
	private final Map<Class<?>, List<?>> rawElements = new Object2ObjectOpenHashMap<>();
	private final Map<Class<?>, Map<Class<?>, List<?>>> rawTypedElements = new Object2ObjectOpenHashMap<>();
	// map of (handler, triple list of (sourceClass, pair of (type, filter), processor))
	private final Map<DataContentType<?, Object>, TriList<Class<?>, Pair<Class<?>, Predicate<?>>, Object>> dataCoverage = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void loadElements(FabricDataGenerator.Pack pack) {
		this.dataCoverage.forEach((handler, tasks) -> {
			BiList<Object, List<?>> contentToProcess = BiList.create();
			tasks.forEach((sourceClass, pair, processor) -> {
				Map<Class<?>, List<?>> typeFiltered = this.rawTypedElements.computeIfAbsent(sourceClass, ignored -> new Object2ObjectOpenHashMap<>());
				if (!typeFiltered.containsKey(pair.first())) {
					typeFiltered.put(
						pair.first(),
						this.rawElements.get(sourceClass)
							.stream()
							.filter(
								element -> pair.first().isAssignableFrom(element.getClass()) ||
									(DataContentResolverImpl.REGISTRY.containsKey(element.getClass())
										&& pair.first().isAssignableFrom(DataContentResolverImpl.REGISTRY.getFirstValue(element.getClass())))
							)
							.flatMap(
								element -> pair.first().isAssignableFrom(element.getClass())
									? Stream.of(element)
									: ((DataContentResolver<Object, ?>) DataContentResolverImpl.REGISTRY.getSecondValue(element.getClass())).resolve(element).stream()
							)
							.toList()
					);
				}
				contentToProcess.add(processor, typeFiltered.get(pair.first()).stream().filter((Predicate) pair.second()).toList());
			});
			handler.handleContent(pack, (BiList) contentToProcess);
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, P> void task(Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, P processor) {
		if (!this.rawElements.containsKey(sourceClass)) {
			this.rawElements.put(sourceClass, ClassContentOperator.extract(sourceClass));
		}
		this.dataCoverage.computeIfAbsent((DataContentType<?, Object>) handler, ignored -> TriList.create())
				.add(sourceClass, Pair.create(type, ignored -> true), processor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, P> void task(Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, Predicate<T> filter, P processor) {
		if (!this.rawElements.containsKey(sourceClass)) {
			this.rawElements.put(sourceClass, ClassContentOperator.extract(sourceClass));
		}
		this.dataCoverage.computeIfAbsent((DataContentType<?, Object>) handler, ignored -> TriList.create())
				.add(sourceClass, Pair.create(type, filter), processor);
	}

	@Override
	public <T, P> ChainManager<T, P> chain(Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, Predicate<T> filter, P processor) {
		this.task(sourceClass, type, handler, filter, processor);
		return new ChainManagerImpl<>(this, sourceClass, type, handler, Predicate.not(filter));
	}

	public static class ChainManagerImpl<T, P> implements ChainManager<T, P> {

		private final DataManager manager;
		private final Class<?> sourceClass;
		private final Class<T> type;
		private final DataContentType<T, P> handler;
		private final Predicate<T> exclusion;

		private ChainManagerImpl(DataManager manager, Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, Predicate<T> exclusion) {
			this.manager = manager;
			this.sourceClass = sourceClass;
			this.type = type;
			this.handler = handler;
			this.exclusion = exclusion;
		}

		@Override
		public ChainManager<T, P> chain(Predicate<T> filter, P processor) {
			this.manager.task(this.sourceClass, this.type, this.handler, element -> this.exclusion.test(element) && filter.test(element), processor);
			return new ChainManagerImpl<>(this.manager, this.sourceClass, this.type, this.handler, element -> this.exclusion.test(element) && !filter.test(element));
		}

		@Override
		public void chain(P processor) {
			this.manager.task(this.sourceClass, this.type, this.handler, this.exclusion, processor);
		}
	}
}
