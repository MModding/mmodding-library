package com.mmodding.library.java.impl.either;

import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.object.EitherMapper;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Internal
public class EitherMapperImpl<F, S, R> implements EitherMapper<F, S, R> {

	private final Function<F, R> mapFirst;
	private final Function<S, R> mapSecond;

	public EitherMapperImpl(Function<F, R> mapFirst, Function<S, R> mapSecond) {
		this.mapFirst = mapFirst;
		this.mapSecond = mapSecond;
	}

	@Override
	public R map(Either<F, S> either) {
		return either.map(this.mapFirst, this.mapSecond);
	}
}
