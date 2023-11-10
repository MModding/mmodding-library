package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TriArrayList<E1, E2, E3> extends ArrayList<Triple<E1, E2, E3>> implements TriList<E1, E2, E3> {

	public TriArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public TriArrayList() {
		super();
	}

	public TriArrayList(@NotNull TriList<E1, E2, E3> c) {
		super(c);
	}

	@Override
	public boolean contains(E1 first, E2 second, E3 third) {
		return this.contains(new ImmutableTriple<>(first, second, third));
	}

	@Override
	public E1 getFirst(int index) {
		return this.get(index).getLeft();
	}

	@Override
	public E2 getSecond(int index) {
		return this.get(index).getMiddle();
	}

	@Override
	public E3 getThird(int index) {
		return this.get(index).getRight();
	}

	@Override
	public boolean add(E1 first, E2 second, E3 third) {
		return this.add(new ImmutableTriple<>(first, second, third));
	}

	@Override
	public boolean remove(E1 first, E2 second, E3 third) {
		return this.remove(new ImmutableTriple<>(first, second, third));
	}

	@Override
	public Triple<E1, E2, E3> set(int index, E1 first, E2 second, E3 third) {
		return this.set(index, new ImmutableTriple<>(first, second, third));
	}

	@Override
	public void forEachFirst(Consumer<? super E1> action) {
		this.forEach(value -> action.accept(value.getLeft()));
	}

	@Override
	public void forEachSecond(Consumer<? super E2> action) {
		this.forEach(value -> action.accept(value.getMiddle()));
	}

	@Override
	public void forEachThird(Consumer<? super E3> action) {
		this.forEach(value -> action.accept(value.getRight()));
	}

	@Override
	public void forEach(TriConsumer<? super E1, ? super E2, ? super E3> action) {
		this.forEach(value -> action.accept(value.getLeft(), value.getMiddle(), value.getRight()));
	}
}
