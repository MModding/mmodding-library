package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class NetworkUUID extends NetworkPrimitive<UUID> implements NetworkSupport {

	public static NetworkUUID of(UUID value) {
		return new NetworkUUID(value);
	}

	private NetworkUUID(UUID value) {
		super(value, PacketByteBuf::writeUuid);
	}
}
