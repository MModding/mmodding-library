package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BiArrayList<E1, E2> extends ArrayList<Pair<E1, E2>> implements BiList<E1, E2> {

	public BiArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public BiArrayList() {
		super();
	}

	public BiArrayList(@NotNull BiList<E1, E2> c) {
		super(c);
	}

	@Override
	public boolean contains(E1 first, E2 second) {
		return this.contains(new ImmutablePair<>(first, second));
	}

	@Override
	public E1 getFirst(int index) {
		return this.get(index).getLeft();
	}

	@Override
	public E2 getSecond(int index) {
		return this.get(index).getRight();
	}

	@Override
	public boolean add(E1 first, E2 second) {
		return this.add(new ImmutablePair<>(first, second));
	}

	@Override
	public boolean remove(E1 first, E2 second) {
		return this.remove(new ImmutablePair<>(first, second));
	}

	@Override
	public Pair<E1, E2> set(int index, E1 first, E2 second) {
		return this.set(index, new ImmutablePair<>(first, second));
	}

	@Override
	public void forEachFirst(Consumer<? super E1> action) {
		this.forEach(value -> action.accept(value.getLeft()));
	}

	@Override
	public void forEachSecond(Consumer<? super E2> action) {
		this.forEach(value -> action.accept(value.getRight()));
	}

	@Override
	public void forEach(BiConsumer<? super E1, ? super E2> action) {
		this.forEach(value -> action.accept(value.getLeft(), value.getRight()));
	}
}
