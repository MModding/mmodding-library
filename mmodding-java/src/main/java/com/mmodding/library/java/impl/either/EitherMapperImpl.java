package com.mmodding.library.java.impl.either;

import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.either.EitherMapper;
import com.mmodding.library.java.api.function.Mapper;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EitherMapperImpl<F, S, R> implements EitherMapper<F, S, R> {

	private final Mapper<F, R> mapFirst;
	private final Mapper<S, R> mapSecond;

	public EitherMapperImpl(Mapper<F, R> mapFirst, Mapper<S, R> mapSecond) {
		this.mapFirst = mapFirst;
		this.mapSecond = mapSecond;
	}

	@Override
	public R map(Either<F, S> either) {
		return either.map(this.mapFirst, this.mapSecond);
	}
}
