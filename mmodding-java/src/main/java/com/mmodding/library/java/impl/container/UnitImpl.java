package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Unit;

public record UnitImpl<E>(E value) implements Unit<E> {
}
