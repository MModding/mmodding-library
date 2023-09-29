package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class TrackedDataHandlerUtils {

	public static <T> TrackedDataHandler<List<T>> createTrackedDataListHandler(WriteAction<T> writeAction, ReadAction<T> readAction) {

		return new TrackedDataHandler<>() {

			@Override
			public void write(PacketByteBuf buf, List<T> value) {
				buf.writeVarInt(value.size());
				value.forEach(element -> writeAction.write(buf, element));
			}

			@Override
			public List<T> read(PacketByteBuf buf) {
				int size = buf.readVarInt();
				List<T> value = new ArrayList<>(size);
				for (int i = 0; i < size; i++) {
					value.add(readAction.read(buf));
				}
				return value;
			}

			@Override
			public List<T> copy(List<T> value) {
				return new ArrayList<>(value);
			}
		};
	}

	public static <T> TrackedDataHandler<T> createTrackedDataHandler(WriteAction<T> writeAction, ReadAction<T> readAction, CopyAction<T> copyAction) {

		return new TrackedDataHandler<>() {

            @Override
            public void write(PacketByteBuf buf, T value) {
				writeAction.write(buf, value);
            }

            @Override
            public T read(PacketByteBuf buf) {
                return readAction.read(buf);
            }

            @Override
            public T copy(T value) {
                return copyAction.copy(value);
            }
        };
	}

	@FunctionalInterface
	public interface WriteAction<T> {

		void write(PacketByteBuf buf, T value);
	}

	@FunctionalInterface
	public interface ReadAction<T> {

		T read(PacketByteBuf buf);
	}

	@FunctionalInterface
	public interface CopyAction<T> {

		T copy(T value);
	}
}
