package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkIntegerArray extends NetworkPrimitive<int[]> implements NetworkSupport {

	public static NetworkIntegerArray of(int[] value) {
		return new NetworkIntegerArray(value);
	}

	private NetworkIntegerArray(int[] value) {
		super(value, PacketByteBuf::writeIntArray);
	}

	static {
		NetworkSupport.register(new Identifier("java", "int_array"), NetworkIntegerArray.class, buf -> NetworkIntegerArray.of(buf.readIntArray()));
	}
}
