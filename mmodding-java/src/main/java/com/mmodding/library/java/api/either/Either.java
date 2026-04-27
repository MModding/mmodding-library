package com.mmodding.library.java.api.either;

import com.mmodding.library.java.api.function.Mapper;
import com.mmodding.library.java.impl.either.EitherImpl;

import java.util.Optional;
import java.util.function.Consumer;

public interface Either<F, S> {

	static <F, S> Either<F, S> ofFirst(F value) {
		return EitherImpl.ofFirst(value);
	}

	static <F, S> Either<F, S> ofSecond(S value) {
		return EitherImpl.ofSecond(value);
	}

	Optional<F> getFirst();

	Optional<S> getSecond();

	<R> R map(Mapper<F, R> mapFirst, Mapper<S, R> mapSecond);

	default <R> Either<R, S> mapFirst(Mapper<F, R> mapper) {
		return this.map(first -> Either.ofFirst(mapper.map(first)), Either::ofSecond);
	}

	default <R> Either<F, R> mapSecond(Mapper<S, R> mapper) {
		return this.map(Either::ofFirst, second -> Either.ofSecond(mapper.map(second)));
	}

	void execute(Consumer<F> executeFirst, Consumer<S> executeSecond);
}
