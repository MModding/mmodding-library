package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.block.impl.wrapper.BlockHeapImpl;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.handler.DataHandler;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.list.TriList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataManagerImpl implements DataManager {

	// dynamic approach
	private final Map<Class<?>, List<?>> rawElements = new Object2ObjectOpenHashMap<>();
	private final Map<Class<?>, Map<Class<?>, List<?>>> rawTypedElements = new Object2ObjectOpenHashMap<>();
	// map of (final handler, list of (source))
	private final Map<FinalDataHandler<?>, List<Class<?>>> finalDataCoverage = new Object2ObjectOpenHashMap<>();
	// map of (process handler, triple list of (source, filter, processor))
	private final Map<DataProcessHandler<?, Object>, TriList<Class<?>, Predicate<?>, Object>> dataProcessCoverage = new Object2ObjectOpenHashMap<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void loadElements(FabricDataGenerator.Pack pack) {
		this.finalDataCoverage.forEach((handler, tasks) -> {
			List<Object> finalContents = new ArrayList<>();
			tasks.forEach(source -> {
				Map<Class<?>, List<?>> typeFiltered = this.fillTypes(source, handler);
				finalContents.addAll(typeFiltered.get(handler.getType()));
			});
			handler.handleContent(pack, (List) finalContents);
		});
		this.dataProcessCoverage.forEach((handler, tasks) -> {
			BiList<Object, List<?>> contentToProcess = BiList.create();
			tasks.forEach((source, filter, processor) -> {
				Map<Class<?>, List<?>> typeFiltered = this.fillTypes(source, handler);
				contentToProcess.add(processor, typeFiltered.get(handler.getType()).stream().filter((Predicate) filter).toList());
			});
			handler.handleContent(pack, (BiList) contentToProcess);
		});
	}

	@SuppressWarnings("unchecked")
	private <T> Map<Class<?>, List<?>> fillTypes(Class<?> source, DataHandler<T> handler) {
		Map<Class<?>, List<?>> typeFiltered = this.rawTypedElements.computeIfAbsent(source, ignored -> new Object2ObjectOpenHashMap<>());
		if (!typeFiltered.containsKey(handler.getType())) {
			typeFiltered.put(
				handler.getType(),
				this.rawElements.get(source)
					.stream()
					.filter(
						element -> handler.getType().isAssignableFrom(element.getClass()) ||
							DataContentResolverImpl.linkExists(element.getClass(), handler.getType())
					)
					.flatMap(
						element -> handler.getType().isAssignableFrom(element.getClass())
							? Stream.of(element)
							: ((DataContentResolver<Object, ?>) DataContentResolverImpl.resolver(element.getClass(), handler.getType())).resolve(element).stream()
					)
					.toList()
			);
		}
		return typeFiltered;
	}

	@Override
	public <T> void task(Class<?> source, FinalDataHandler<T> handler) {
		if (!this.rawElements.containsKey(source)) this.rawElements.put(source, ClassContentOperator.extract(source));
		this.finalDataCoverage.computeIfAbsent(handler, ignored -> new ArrayList<>()).add(source);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, P processor) {
		if (!this.rawElements.containsKey(source)) this.rawElements.put(source, ClassContentOperator.extract(source));
		this.dataProcessCoverage.computeIfAbsent((DataProcessHandler<?, Object>) handler, ignored -> TriList.create())
				.add(source, ignored -> true, processor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, Predicate<T> filter, P processor) {
		if (!this.rawElements.containsKey(source)) this.rawElements.put(source, ClassContentOperator.extract(source));
		this.dataProcessCoverage.computeIfAbsent((DataProcessHandler<?, Object>) handler, ignored -> TriList.create())
				.add(source, filter, processor);
	}

	@Override
	public <T, P> ChainManager<T, P> chain(Class<?> source, DataProcessHandler<T, P> handler) {
		return new ChainManagerImpl<>(this, source, handler, ignored -> true);
	}

	public static class ChainManagerImpl<T, P> implements ChainManager<T, P> {

		private final DataManager manager;
		private final Class<?> source;
		private final DataProcessHandler<T, P> handler;
		private final Predicate<T> exclusion;

		private ChainManagerImpl(DataManager manager, Class<?> source, DataProcessHandler<T, P> handler, Predicate<T> exclusion) {
			this.manager = manager;
			this.source = source;
			this.handler = handler;
			this.exclusion = exclusion;
		}

		@Override
		public ChainManager<T, P> chain(Predicate<T> filter, P processor) {
			this.manager.task(this.source, this.handler, element -> this.exclusion.test(element) && filter.test(element), processor);
			return new ChainManagerImpl<>(this.manager, this.source, this.handler, element -> this.exclusion.test(element) && !filter.test(element));
		}

		@Override
		public void chain(P processor) {
			this.manager.task(this.source, this.handler, this.exclusion, processor);
		}
	}

	static {
		DataContentResolver.register(BlockHeapImpl.class, Block.class, BlockHeap::getEntries);
	}
}
