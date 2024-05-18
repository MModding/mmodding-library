package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Pair;

public record PairImpl<E1, E2>(E1 first, E2 second) implements Pair<E1, E2> {
}
