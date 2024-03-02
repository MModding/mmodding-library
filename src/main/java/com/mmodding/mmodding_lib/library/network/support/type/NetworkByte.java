package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkByte extends NetworkPrimitive<Byte> implements NetworkSupport {

	public static NetworkByte of(byte value) {
		return new NetworkByte(value);
	}

	private NetworkByte(byte value) {
		super(value, PacketByteBuf::writeByte);
	}
}
