package com.mmodding.library.network.api;

import com.mmodding.library.core.api.management.content.InjectedContent;
import net.minecraft.network.PacketByteBuf;

import java.util.Optional;

@InjectedContent(PacketByteBuf.class)
public interface PacketByteBufExtension {

	/**
	 * Determines the type of the next handled value, and stores it in an {@link Optional} instance
	 * if the next value is a handled value, otherwise it returns {@link Optional#empty()}
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
	 * @param type the type of the handled value.
	 * @param value the written value
	 */
	default <T> void writeByHandling(Class<T> type, T value) {
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
	 * @param type the type of the handled value
	 * @param value the written value as an optional instance
	 */
	default <T> void writeOptionalByHandling(Class<T> type, T value) {
		throw new IllegalStateException();
	}
}
