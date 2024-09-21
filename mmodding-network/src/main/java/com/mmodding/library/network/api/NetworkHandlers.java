package com.mmodding.library.network.api;

import com.mmodding.library.network.impl.NetworkHandlersImpl;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkHandlers {

	/**
	 * Unlike {@link FabricPacket} and {@link PacketType}, which are made to handle modded packets,
	 * {@link NetworkHandlers} are made to let you encode or decode non-packet objects by using the
	 * {@link PacketByteBufExtension} of a {@link PacketByteBuf}.
	 * @param type the type of the object, represented as its {@link Class} object
	 * @param identifier the identifier representation of the type
	 * @param reader the reader factory
	 * @param writer the writer factory
	 */
	public static <T> void register(Class<T> type, Identifier identifier, PacketByteBuf.Reader<T> reader, PacketByteBuf.Writer<T> writer) {
		NetworkHandlersImpl.register(type, identifier, reader, writer);
	}
}
