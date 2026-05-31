package com.mmodding.library.core.api.serialization;

import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.function.*;
import com.mmodding.library.java.api.list.BiList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("DuplicatedCode")
public class MModdingDecoders {

	public static <R> Decoder<R> lazyInitialized(Supplier<Decoder<R>> delegate) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<R, T>> decode(DynamicOps<T> ops, T input) {
				return delegate.get().decode(ops, input);
			}
		};
	}

	public static <R> MapDecoder<R> optionalFieldOf(Decoder<R> decoder, String name, R defaultValue) {
		return MModdingDecoders.optionalFieldOf(decoder, name, defaultValue, false);
	}

	public static <R> MapDecoder<R> optionalFieldOf(Decoder<R> decoder, String name, R defaultValue, boolean lenient) {
		return MModdingDecoders.optionalFieldOf(decoder, name, lenient).map(optional -> optional.orElse(defaultValue));
	}

	public static <R> MapDecoder<Optional<R>> optionalFieldOf(Decoder<R> decoder, String name) {
		return MModdingDecoders.optionalFieldOf(decoder, name, false);
	}

	public static <R> MapDecoder<Optional<R>> optionalFieldOf(Decoder<R> decoder, String name, boolean lenient) {
		return new CompressableMapDecoder<>() {

			@Override
			public <T> DataResult<Optional<R>> decode(DynamicOps<T> ops, MapLike<T> input) {
				T value = input.get(name);
				if (value == null) {
					return DataResult.success(Optional.empty());
				}
				DataResult<R> parsed = decoder.parse(ops, value);
				if (parsed.isError() && lenient) {
					return DataResult.success(Optional.empty());
				}
				return parsed.map(Optional::of).setPartial(parsed.resultOrPartial());
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.of(ops.createString(name));
			}
		};
	}

	public static <R> Decoder<List<R>> listOf(Decoder<R> decoder) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<List<R>, T>> decode(DynamicOps<T> ops, T input) {
				List<R> result = new ArrayList<>();
				ops.getList(input).getOrThrow().accept(t -> result.add(decoder.parse(ops, t).getOrThrow()));
				return DataResult.success(Pair.of(result, input));
			}
		};
	}

	public static <F, S> Decoder<BiList<F, S>> compoundList(Decoder<F> keyDecoder, Decoder<S> valueDecoder) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<BiList<F, S>, T>> decode(DynamicOps<T> ops, T input) {
				BiList<F, S> result = BiList.create();
				ops.getMapEntries(input).getOrThrow().accept((k, v) -> result.add(keyDecoder.parse(ops, k).getOrThrow(), valueDecoder.parse(ops, v).getOrThrow()));
				return DataResult.success(Pair.of(result, input));
			}
		};
	}

	public static <R> Decoder<R> alternative(Decoder<R> fc, Decoder<R> sc) {
		return MModdingDecoders.either(fc, sc)
			.map(e -> e.map(AutoMapper.identity(), AutoMapper.identity()));
	}

	public static <F, S> Decoder<Either<F, S>> either(Decoder<F> fc, Decoder<S> sc) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<Either<F, S>, T>> decode(final DynamicOps<T> ops, final T input) {
				DataResult<Pair<Either<F, S>, T>> fr = fc.decode(ops, input).map(vo -> vo.mapFirst(Either::ofFirst));
				if (fr.isSuccess()) {
					return fr;
				}
				DataResult<Pair<Either<F, S>, T>> sr = sc.decode(ops, input).map(vo -> vo.mapFirst(Either::ofSecond));
				if (sr.isSuccess()) {
					return sr;
				}
				if (fr.hasResultOrPartial()) {
					return fr;
				}
				if (sr.hasResultOrPartial()) {
					return sr;
				}
				return DataResult.error(() -> "Failed to parse either. First: " + fr.error().orElseThrow().message() + "; Second: " + sr.error().orElseThrow().message());
			}
		};
	}

	public static <A, T1> Decoder<A> recordDecoder(MapDecoder<T1> t1c, Function<T1, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1), input));
			}
		};
	}

	public static <A, T1, T2> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, BiFunction<T1, T2, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2), input));
			}
		};
	}

	public static <A, T1, T2, T3> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, TriFunction<T1, T2, T3, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3), input));
			}
		};
	}

	public static <A, T1, T2, T3, T4> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, MapDecoder<T4> t4c, QuartFunction<T1, T2, T3, T4, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				T4 t4 = t4c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3, t4), input));
			}
		};
	}

	public static <A, T1, T2, T3, T4, T5> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, MapDecoder<T4> t4c, MapDecoder<T5> t5c, QuinFunction<T1, T2, T3, T4, T5, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				T4 t4 = t4c.decode(ops, map).getOrThrow();
				T5 t5 = t5c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3, t4, t5), input));
			}
		};
	}

	public static <A, T1, T2, T3, T4, T5, T6> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, MapDecoder<T4> t4c, MapDecoder<T5> t5c, MapDecoder<T6> t6c, SextFunction<T1, T2, T3, T4, T5, T6, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				T4 t4 = t4c.decode(ops, map).getOrThrow();
				T5 t5 = t5c.decode(ops, map).getOrThrow();
				T6 t6 = t6c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3, t4, t5, t6), input));
			}
		};
	}

	public static <A, T1, T2, T3, T4, T5, T6, T7> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, MapDecoder<T4> t4c, MapDecoder<T5> t5c, MapDecoder<T6> t6c, MapDecoder<T7> t7c, SeptFunction<T1, T2, T3, T4, T5, T6, T7, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				T4 t4 = t4c.decode(ops, map).getOrThrow();
				T5 t5 = t5c.decode(ops, map).getOrThrow();
				T6 t6 = t6c.decode(ops, map).getOrThrow();
				T7 t7 = t7c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3, t4, t5, t6, t7), input));
			}
		};
	}

	public static <A, T1, T2, T3, T4, T5, T6, T7, T8> Decoder<A> recordDecoder(MapDecoder<T1> t1c, MapDecoder<T2> t2c, MapDecoder<T3> t3c, MapDecoder<T4> t4c, MapDecoder<T5> t5c, MapDecoder<T6> t6c, MapDecoder<T7> t7c, MapDecoder<T8> t8c, OctFunction<T1, T2, T3, T4, T5, T6, T7, T8, A> constructor) {
		return new Decoder<>() {

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				MapLike<T> map = ops.getMap(input).getOrThrow((error) -> new IllegalStateException("Not a map: " + error));
				T1 t1 = t1c.decode(ops, map).getOrThrow();
				T2 t2 = t2c.decode(ops, map).getOrThrow();
				T3 t3 = t3c.decode(ops, map).getOrThrow();
				T4 t4 = t4c.decode(ops, map).getOrThrow();
				T5 t5 = t5c.decode(ops, map).getOrThrow();
				T6 t6 = t6c.decode(ops, map).getOrThrow();
				T7 t7 = t7c.decode(ops, map).getOrThrow();
				T8 t8 = t8c.decode(ops, map).getOrThrow();
				return DataResult.success(Pair.of(constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8), input));
			}
		};
	}
}
