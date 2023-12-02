package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkByteArray extends NetworkPrimitive<byte[]> implements NetworkSupport {

	public static NetworkByteArray of(byte[] value) {
		return new NetworkByteArray(value);
	}

	private NetworkByteArray(byte[] value) {
		super(value, PacketByteBuf::writeByteArray);
	}

	static {
		NetworkSupport.register(new Identifier("java", "byte_array"), NetworkByteArray.class, buf -> NetworkByteArray.of(buf.readByteArray()));
	}
}
