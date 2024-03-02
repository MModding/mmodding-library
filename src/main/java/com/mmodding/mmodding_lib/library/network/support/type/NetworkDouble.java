package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkDouble extends NetworkPrimitive<Double> implements NetworkSupport {

	public static NetworkDouble of(double value) {
		return new NetworkDouble(value);
	}

	private NetworkDouble(double value) {
		super(value, PacketByteBuf::writeDouble);
	}
}
