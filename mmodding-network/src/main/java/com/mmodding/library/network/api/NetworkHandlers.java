package com.mmodding.library.network.api;

import com.mmodding.library.network.impl.NetworkHandlersImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.resources.Identifier;

public class NetworkHandlers {

	/**
	 * {@link NetworkHandlers} are made to let you encode or decode non-packet objects by using the
	 * {@link FriendlyByteBufExtension} of a {@link FriendlyByteBuf}.
	 * @param type the type of the object, represented as its {@link Class} object
	 * @param identifier the identifier representation of the type
	 * @param reader the reader factory
	 * @param writer the writer factory
	 */
	public static <T> void register(Class<T> type, Identifier identifier, StreamDecoder<? extends FriendlyByteBuf, T> reader, StreamEncoder<? extends FriendlyByteBuf, T> writer) {
		NetworkHandlersImpl.register(type, identifier, reader, writer);
	}
}
