package com.mmodding.library.java.impl.list;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.list.TriList;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TriListImpl<E1, E2, E3> extends ArrayList<Triple<E1, E2, E3>> implements TriList<E1, E2, E3> {

	public TriListImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public TriListImpl() {
		super();
	}

	public TriListImpl(@NotNull TriList<E1, E2, E3> c) {
		super(c);
	}

	@Override
	public boolean contains(E1 first, E2 second, E3 third) {
		return this.contains(Triple.create(first, second, third));
	}

	@Override
	public E1 getFirst(int index) {
		return this.get(index).first();
	}

	@Override
	public E2 getSecond(int index) {
		return this.get(index).second();
	}

	@Override
	public E3 getThird(int index) {
		return this.get(index).third();
	}

	@Override
	public boolean add(E1 first, E2 second, E3 third) {
		return this.add(Triple.create(first, second, third));
	}

	@Override
	public boolean remove(E1 first, E2 second, E3 third) {
		return this.remove(Triple.create(first, second, third));
	}

	@Override
	public Triple<E1, E2, E3> set(int index, E1 first, E2 second, E3 third) {
		return this.set(index, Triple.create(first, second, third));
	}

	@Override
	public void forEachFirst(Consumer<? super E1> action) {
		this.forEach(value -> action.accept(value.first()));
	}

	@Override
	public void forEachSecond(Consumer<? super E2> action) {
		this.forEach(value -> action.accept(value.second()));
	}

	@Override
	public void forEachThird(Consumer<? super E3> action) {
		this.forEach(value -> action.accept(value.third()));
	}

	@Override
	public void forEach(TriConsumer<? super E1, ? super E2, ? super E3> action) {
		this.forEach(value -> action.accept(value.first(), value.second(), value.third()));
	}
}
