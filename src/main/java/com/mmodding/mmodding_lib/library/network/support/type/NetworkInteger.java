package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkInteger extends NetworkPrimitive<Integer> implements NetworkSupport {

	public static NetworkInteger of(int value) {
		return new NetworkInteger(value);
	}

	private NetworkInteger(int value) {
		super(value, PacketByteBuf::writeVarInt);
	}
}
