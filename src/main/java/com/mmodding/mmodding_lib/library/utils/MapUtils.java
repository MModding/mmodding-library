package com.mmodding.mmodding_lib.library.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MapUtils {

	public static <K, V> Map<K, V> builder(Consumer<Map<K, V>> consumer) {
		Map<K, V> map = new HashMap<>();
		consumer.accept(map);
		return map;
	}

	public static <K, V1, V2> BiMap<K, V1, V2> biBuilder(Consumer<BiMap<K, V1, V2>> consumer) {
		BiMap<K, V1, V2> biMap = new BiHashMap<>();
		consumer.accept(biMap);
		return biMap;
	}

	public static <K, V1, V2, V3> TriMap<K, V1, V2, V3> triBuilder(Consumer<TriMap<K, V1, V2, V3>> consumer) {
		TriMap<K, V1, V2, V3> triMap = new TriHashMap<>();
		consumer.accept(triMap);
		return triMap;
	}

	public static <K> MixedMap<K> mixedBuilder(Consumer<MixedMap<K>> consumer) {
		MixedMap<K> mixedMap = new MixedHashMap<>();
		consumer.accept(mixedMap);
		return mixedMap;
	}

	public static <K, V> V consume(Map<K, V> map, K key) {
		V value = map.get(key);
		map.remove(key);
		return value;
	}

	@SafeVarargs
	public static <K> K untilNotContainingKey(Supplier<K> key, Map<K, ?>... maps) {
		boolean exit = false;
		K obtained = null;
		while (!exit) {
			obtained = key.get();
			boolean contains = false;
			for (Map<K, ?> map : maps) {
                if (map.containsKey(obtained)) {
					contains = true;
					break;
				}
			}
			exit = !contains;
		}
		return obtained;
	}

	public static <K, V> List<Map<K, V>> divide(Map<K, V> map, int limit) {
		List<Map<K, V>> maps = new ArrayList<>();
		AtomicInteger current = new AtomicInteger();
		map.forEach(
			(key, value) -> {
				if (maps.isEmpty()) {
					maps.add(new HashMap<>());
				}
				if (current.incrementAndGet() >= limit) {
					maps.add(new HashMap<>());
					current.set(0);
				}
				maps.get(maps.size() - 1).put(key, value);
			}
		);
		return maps;
	}
}
