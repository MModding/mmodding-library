package com.mmodding.library.java.impl.list;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.BiConsumer;

@SuppressWarnings("unchecked")
public class MixedListImpl extends ArrayList<Typed<?>> implements MixedList {

	public MixedListImpl(int initialCapacity) {
		super(initialCapacity);
	}

	public MixedListImpl() {
		super();
	}

	public MixedListImpl(@NotNull MixedList c) {
		super(c);
	}

	@Override
	public <E> boolean contains(Class<E> type, E e) {
		return super.contains(Typed.of(type, e));
	}

	@Override
	public <E> E get(int index, Class<E> type) {
		Typed<?> typed = super.get(index);
		if (typed == null) {
			typed = MixedMap.emptyValue(type);
		}
		if (type.equals(typed.getType())) {
			return (E) typed.getValue();
		}
		else {
			throw new IllegalArgumentException("Given type does not match the targeted type!");
		}
	}

	@Override
	public <E> boolean add(Class<E> type, E e) {
		return super.add(Typed.of(type, e));
	}

	@Override
	public <E> boolean remove(Class<E> type, E e) {
		return super.remove(Typed.of(type, e));
	}

	@Override
	public <E> E set(int index, Class<E> type, E element) {
		return (E) super.set(index, Typed.of(type, element)).getValue();
	}

	@Override
	public <E> void forEach(BiConsumer<? super Class<E>, ? super E> action) {
		this.forEach(value -> action.accept((Class<E>) value.getType(), (E) value.getValue()));
	}
}
