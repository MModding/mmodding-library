package com.mmodding.library.network.api;

import com.mmodding.library.network.impl.NetworkHandlersImpl;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandlers {

	/**
	 * Unlike {@link FabricPacket} and {@link PacketType}, which are made to handle modded packets,
	 * {@link NetworkHandlers} are made to let you encode or decode non-packet objects by using the
	 * {@link FriendlyByteBufExtension} of a {@link FriendlyByteBuf}.
	 * @param type the type of the object, represented as its {@link Class} object
	 * @param identifier the identifier representation of the type
	 * @param reader the reader factory
	 * @param writer the writer factory
	 */
	public static <T> void register(Class<T> type, ResourceLocation identifier, FriendlyByteBuf.Reader<T> reader, FriendlyByteBuf.Writer<T> writer) {
		NetworkHandlersImpl.register(type, identifier, reader, writer);
	}
}
