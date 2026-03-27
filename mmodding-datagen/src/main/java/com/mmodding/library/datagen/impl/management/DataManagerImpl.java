package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.datagen.impl.management.tag.TagComposerImpl;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.list.TriList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

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
	// map of (registry, tiple list of (sourceClass, filter, tag)
	private final Map<RegistryKey<? extends Registry<?>>, TriList<Class<?>, Predicate<?>, TagKey<?>>> tagCoverage = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void loadTags(FabricDataGenerator.Pack pack) {
		this.tagCoverage.forEach((registry, infos) -> {
			BiList<Object, List<?>> contentToProcess = BiList.create();
			Class<?> type = Registries.REGISTRIES.getOrThrow((RegistryKey) registry).stream().findFirst().orElseThrow().getClass();
			infos.forEach((sourceClass, filter, tag) -> {
				Map<Class<?>, List<?>> typeFiltered = this.ensureTypeCollection(sourceClass, type);
				contentToProcess.add(tag, typeFiltered.get(type).stream().filter((Predicate) filter).toList());
			});
			pack.addProvider((output, future) -> new TagComposerImpl<>((RegistryKey) registry, (BiList) contentToProcess, output, future));
		});
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void loadElements(FabricDataGenerator.Pack pack) {
		this.dataCoverage.forEach((handler, tasks) -> {
			BiList<Object, List<?>> contentToProcess = BiList.create();
			tasks.forEach((sourceClass, pair, processor) -> {
				Map<Class<?>, List<?>> typeFiltered = this.ensureTypeCollection(sourceClass, pair.first());
				contentToProcess.add(processor, typeFiltered.get(pair.first()).stream().filter((Predicate) pair.second()).toList());
			});
			handler.handleContent(pack, (BiList) contentToProcess);
		});
	}

	@SuppressWarnings("unchecked")
	private Map<Class<?>, List<?>> ensureTypeCollection(Class<?> sourceClass, Class<?> type) {
		Map<Class<?>, List<?>> typeFiltered = this.rawTypedElements.computeIfAbsent(sourceClass, ignored -> new Object2ObjectOpenHashMap<>());
		if (!typeFiltered.containsKey(type)) {
			typeFiltered.put(
				type,
				this.rawElements.get(sourceClass)
					.stream()
					.filter(
						element -> type.isAssignableFrom(element.getClass()) ||
							(DataContentResolverImpl.REGISTRY.containsKey(element.getClass())
								&& type.isAssignableFrom(DataContentResolverImpl.REGISTRY.getFirstValue(element.getClass())))
					)
					.flatMap(
						element -> type.isAssignableFrom(element.getClass())
							? Stream.of(element)
							: ((DataContentResolver<Object, ?>) DataContentResolverImpl.REGISTRY.getSecondValue(element.getClass())).resolve(element).stream()
					)
					.toList()
			);
		}
		return typeFiltered;
	}

	@Override
	public <T> void tag(Class<?> sourceClass, RegistryKey<? extends Registry<T>> registry, Predicate<T> filter, TagKey<T> tag) {
		if (!this.rawElements.containsKey(sourceClass)) {
			this.rawElements.put(sourceClass, ClassContentOperator.extract(sourceClass));
		}
		this.tagCoverage.computeIfAbsent(registry, ignored -> TriList.create())
			.add(sourceClass, filter, tag);
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
