package com.mmodding.library.network.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import net.minecraft.network.PacketByteBuf;

import java.util.Optional;

@InjectedContent(PacketByteBuf.class)
public interface PacketByteBufExtension {

	/**
	 * Determines the type of the next handled value, and stores it in an {@link Optional} instance
	 * if the next value is a handled value, otherwise it returns {@link Optional#empty()}.
	 * @return the next type
	 */
	default Optional<Class<?>> peekNextType() {
		throw new IllegalStateException();
	}

	/**
	 * Reads the next handled value.
	 * @param type the type of the next handled value
	 * @return the read value
	 */
	default <T> T readByHandling(Class<T> type) {
		throw new IllegalStateException();
	}

	/**
	 * Writes a handled value.
	 * @param value the written value
	 */
	default <T> void writeByHandling(T value) {
		throw new IllegalStateException();
	}

	/**
	 * Reads the next handled value as an {@link Optional} instance.
	 * @param type the type of the next handled value
	 * @return the read value as an optional instance
	 */
	default <T> Optional<T> readOptionalByHandling(Class<T> type) {
		throw new IllegalStateException();
	}

	/**
	 * Writes a handled value as an {@link Optional} instance.
	 * @param value the written value as an optional instance
	 */
	default <T> void writeOptionalByHandling(T value) {
		throw new IllegalStateException();
	}

	/**
	 * Reads a {@link MixedList} object.
	 * @return the {@link MixedList} object
	 */
	default MixedList readMixedList() {
		throw new IllegalStateException();
	}

	/**
	 * Writes a {@link MixedList} object.
	 * @param list the {@link MixedList} object
	 */
	default void writeMixedList(MixedList list) {
		throw new IllegalStateException();
	}

	/**
	 * Reads a {@link MixedMap} object with a specific entry reader.
	 * @param entryReader the entry reader
	 * @return the {@link MixedMap} object
	 */
	default <T> MixedMap<T> readMixedMap(PacketByteBuf.PacketReader<T> entryReader) {
		throw new IllegalStateException();
	}

	/**
	 * Writes a {@link MixedMap} object with a specific entry writer.
	 * @param map the {@link MixedMap} object
	 * @param entryWriter the entry writer
	 */
	default <T> void writeMixedMap(MixedMap<T> map, PacketByteBuf.PacketWriter<T> entryWriter) {
		throw new IllegalStateException();
	}
}
