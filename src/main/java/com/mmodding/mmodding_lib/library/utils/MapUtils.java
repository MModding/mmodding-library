package com.mmodding.mmodding_lib.library.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
}
