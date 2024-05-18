package com.mmodding.library.java.api.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListUtil {

	public static <E> List<E> builder(Consumer<List<E>> consumer) {
		List<E> list = new ArrayList<>();
		consumer.accept(list);
		return list;
	}

	public static <E1, E2> BiList<E1, E2> biBuilder(Consumer<BiList<E1, E2>> consumer) {
		BiList<E1, E2> biList = BiList.create();
		consumer.accept(biList);
		return biList;
	}

	public static <E1, E2, E3> TriList<E1, E2, E3> triBuilder(Consumer<TriList<E1, E2, E3>> consumer) {
		TriList<E1, E2, E3> triList = TriList.create();
		consumer.accept(triList);
		return triList;
	}

	public static MixedList mixedList(Consumer<MixedList> consumer) {
		MixedList mixedList = MixedList.create();
		consumer.accept(mixedList);
		return mixedList;
	}
}
