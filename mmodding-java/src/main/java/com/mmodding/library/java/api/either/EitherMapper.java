package com.mmodding.library.java.api.either;

import com.mmodding.library.java.api.function.Mapper;
import com.mmodding.library.java.impl.either.EitherMapperImpl;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface EitherMapper<F, S, R> {

	static <F, S, R> EitherMapper<F, S ,R> create(Mapper<F, R> mapFirst, Mapper<S, R> mapSecond) {
		return new EitherMapperImpl<>(mapFirst, mapSecond);
	}

	R map(Either<F, S> either);
}
