package com.mmodding.library.java.impl.either;

import com.mmodding.library.java.api.either.Either;

import java.util.Optional;
import java.util.function.Function;

public class EitherImpl<F, S> implements Either<F, S> {

	private F firstValue;
	private S secondValue;

	public static <F, S> Either<F, S> ofFirst(F value) {
		EitherImpl<F, S> either = new EitherImpl<>();
		either.firstValue = value;
		return either;
	}

	public static <F, S> Either<F, S> ofSecond(S value) {
		EitherImpl<F, S> either = new EitherImpl<>();
		either.secondValue = value;
		return either;
	}

	public EitherImpl() {
		this.firstValue = null;
		this.secondValue = null;
	}

	@Override
	public Optional<F> getFirst() {
		return Optional.ofNullable(this.firstValue);
	}

	@Override
	public Optional<S> getSecond() {
		return Optional.ofNullable(this.secondValue);
	}

	@Override
	public <R> R map(Function<F, R> mapFirst, Function<S, R> mapSecond) {
		if (this.firstValue != null) {
			return mapFirst.apply(this.firstValue);
		}
		else {
			return mapSecond.apply(this.secondValue);
		}
	}
}
