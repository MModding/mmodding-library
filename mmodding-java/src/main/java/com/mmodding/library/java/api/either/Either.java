package com.mmodding.library.java.api.either;

import com.mmodding.library.java.impl.either.EitherImpl;

import java.util.Optional;
import java.util.function.Function;

public interface Either<F, S> {

	static <F, S> Either<F, S> ofFirst(F value) {
		return EitherImpl.ofFirst(value);
	}

	static <F, S> Either<F, S> ofSecond(S value) {
		return EitherImpl.ofSecond(value);
	}

	Optional<F> getFirst();

	Optional<S> getSecond();

	<R> R map(Function<F, R> mapFirst, Function<S, R> mapSecond);
}
