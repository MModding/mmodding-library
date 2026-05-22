package com.mmodding.library.network.api;

import com.mmodding.library.java.api.container.Typed;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * An abstraction for {@link StreamCodec} managing dynamically typed objects.
 * <br>It is powering {@link MModdingStreamCodecs#mixedList(Map)} and {@link MModdingStreamCodecs#mixedMap(StreamCodec, Map)}.
 * @param <B> the byte buff class type
 * @param <T> the codec target element class type
 */
public abstract class DynamicTypeSupportStreamCodec<B extends ByteBuf, T> implements StreamCodec<B, T> {

	private final Map<Integer, Class<?>> hashedTypes;
	private final Map<Integer, StreamCodec<B, Object>> supportedTypes;

	protected DynamicTypeSupportStreamCodec(Map<Class<?>, StreamCodec<B, Object>> supportedTypes) {
		this.hashedTypes = supportedTypes.keySet()
			.stream()
			.map(clazz -> Map.entry(clazz.hashCode(), clazz))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		this.supportedTypes = supportedTypes.entrySet()
			.stream()
			.map(e -> Map.entry(e.getKey().hashCode(), e.getValue()))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	protected Typed<?> decodeTyped(B input, String unsupportedTypeMessage) {
		int hash = input.readInt();
		StreamCodec<B, Object> elementStreamCodec = this.supportedTypes.get(hash);
		if (elementStreamCodec == null) {
			throw new IllegalStateException(unsupportedTypeMessage);
		}
		return Typed.of(this.hashedTypes.get(hash), elementStreamCodec.decode(input));
	}

	protected void encodeTyped(B output, Typed<?> typed) {
		int hash = typed.getType().hashCode();
		if (!this.supportedTypes.containsKey(hash)) {
			throw new IllegalStateException("No network serializer for type: " + typed.getType());
		}
		output.writeInt(hash);
		this.supportedTypes.get(hash).encode(output, typed.getValue());
	}
}
