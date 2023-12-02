package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkInteger extends NetworkPrimitive<Integer> implements NetworkSupport {

	public static NetworkInteger of(int value) {
		return new NetworkInteger(value);
	}

	private NetworkInteger(int value) {
		super(value, PacketByteBuf::writeVarInt);
	}

	static {
		NetworkSupport.register(new Identifier("java", "integer"), NetworkInteger.class, buf -> NetworkInteger.of(buf.readVarInt()));
	}
}
