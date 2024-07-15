package com.mmodding.library.network.api;

import com.mmodding.library.network.impl.NetworkHandlersImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkHandlers {

	public static <T> void register(Class<T> type, Identifier identifier, PacketByteBuf.Reader<T> reader, PacketByteBuf.Writer<T> writer) {
		NetworkHandlersImpl.register(type, identifier, reader, writer);
	}
}
