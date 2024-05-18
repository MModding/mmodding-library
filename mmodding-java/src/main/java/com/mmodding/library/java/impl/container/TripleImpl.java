package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Triple;

public record TripleImpl<E1, E2, E3>(E1 first, E2 second, E3 third) implements Triple<E1, E2, E3> {
}
