package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkFloat extends NetworkPrimitive<Float> implements NetworkSupport {

	public static NetworkFloat of(float value) {
		return new NetworkFloat(value);
	}

	private NetworkFloat(float value) {
		super(value, PacketByteBuf::writeFloat);
	}
}
