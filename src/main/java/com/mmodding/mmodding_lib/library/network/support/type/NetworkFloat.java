package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkFloat extends NetworkPrimitive<Float> implements NetworkSupport {

	public static NetworkFloat of(float value) {
		return new NetworkFloat(value);
	}

	private NetworkFloat(float value) {
		super(value, PacketByteBuf::writeFloat);
	}

	static {
		NetworkSupport.register(new Identifier("java", "float"), NetworkFloat.class, buf -> NetworkFloat.of(buf.readFloat()));
	}
}
