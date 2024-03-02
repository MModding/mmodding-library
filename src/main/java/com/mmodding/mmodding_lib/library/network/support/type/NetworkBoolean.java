package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkBoolean extends NetworkPrimitive<Boolean> implements NetworkSupport {

	public static NetworkBoolean of(Boolean value) {
		return new NetworkBoolean(value);
	}

	private NetworkBoolean(Boolean value) {
		super(value, PacketByteBuf::writeBoolean);
	}
}
