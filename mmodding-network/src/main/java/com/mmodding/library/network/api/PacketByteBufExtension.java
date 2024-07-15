package com.mmodding.library.network.api;

import com.mmodding.library.core.api.management.content.InjectedContent;
import net.minecraft.network.PacketByteBuf;

import java.util.Optional;

@InjectedContent(PacketByteBuf.class)
public interface PacketByteBufExtension {

	default Optional<Class<?>> peekNextType() {
		throw new IllegalStateException();
	}

	default <T> T readByHandling(Class<T> type) {
		throw new IllegalStateException();
	}

	default <T> void writeByHandling(Class<T> type, T value) {
		throw new IllegalStateException();
	}

	default <T> Optional<T> readOptionalByHandling(Class<T> type) {
		throw new IllegalStateException();
	}

	default <T> void writeOptionalByHandling(Class<T> type, T value) {
		throw new IllegalStateException();
	}
}
