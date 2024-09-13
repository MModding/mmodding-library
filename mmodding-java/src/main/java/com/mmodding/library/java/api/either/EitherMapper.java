package com.mmodding.library.java.api.either;

import com.mmodding.library.java.impl.either.EitherMapperImpl;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.NonExtendable
public interface EitherMapper<F, S, R> {

	static <F, S, R> EitherMapper<F, S ,R> create(Function<F, R> mapFirst, Function<S, R> mapSecond) {
		return new EitherMapperImpl<>(mapFirst, mapSecond);
	}

	R map(Either<F, S> either);
}
