package com.mmodding.library.java.impl.list;

import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.container.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BiListImpl<E1, E2> extends ArrayList<Pair<E1, E2>> implements BiList<E1, E2> {

	public BiListImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public BiListImpl() {
		super();
	}

	public BiListImpl(@NotNull BiList<E1, E2> c) {
		super(c);
	}

	@Override
	public boolean contains(E1 first, E2 second) {
		return this.contains(Pair.create(first, second));
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
	public boolean add(E1 first, E2 second) {
		return this.add(Pair.create(first, second));
	}

	@Override
	public boolean remove(E1 first, E2 second) {
		return this.remove(Pair.create(first, second));
	}

	@Override
	public Pair<E1, E2> set(int index, E1 first, E2 second) {
		return this.set(index, Pair.create(first, second));
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
	public void forEach(BiConsumer<? super E1, ? super E2> action) {
		this.forEach(value -> action.accept(value.first(), value.second()));
	}
}
