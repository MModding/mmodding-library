package com.mmodding.mmodding_lib.library.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.BiConsumer;

@SuppressWarnings("unchecked")
public class MixedArrayList extends ArrayList<TypedObject<?>> implements MixedList {

	public MixedArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public MixedArrayList() {
		super();
	}

	public MixedArrayList(@NotNull MixedList c) {
		super(c);
	}

	@Override
	public <E> boolean contains(Class<E> type, E e) {
		return super.contains(TypedObject.of(type, e));
	}

	@Override
	public <E> E get(Class<E> type, int index) {
		TypedObject<?> typed = super.get(index);
		if (type.equals(typed.getType())) {
			return (E) typed.getValue();
		}
		else {
			throw new IllegalArgumentException("Given type does not match the targeted type!");
		}
	}

	@Override
	public <E> boolean add(Class<E> type, E e) {
		return super.add(TypedObject.of(type, e));
	}

	@Override
	public <E> boolean remove(Class<E> type, E e) {
		return super.remove(TypedObject.of(type, e));
	}

	@Override
	public <E> E set(int index, Class<E> type, E element) {
		return (E) super.set(index, TypedObject.of(type, element)).getValue();
	}

	@Override
	public <E> void forEach(BiConsumer<? super Class<E>, ? super E> action) {
		this.forEach(value -> action.accept((Class<E>) value.getType(), (E) value.getValue()));
	}
}
