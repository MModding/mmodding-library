package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public class NetworkLongArray extends NetworkPrimitive<long[]> implements NetworkSupport {

	public static NetworkLongArray of(long[] value) {
		return new NetworkLongArray(value);
	}

	private NetworkLongArray(long[] value) {
		super(value, PacketByteBuf::writeLongArray);
	}
}
