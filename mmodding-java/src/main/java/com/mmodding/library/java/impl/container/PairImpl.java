package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Pair;

public record PairImpl<E0, E1>(E0 first, E1 second) implements Pair<E0, E1> {
}
