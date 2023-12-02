package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkString extends NetworkPrimitive<String> implements NetworkSupport {

	public static NetworkString of(String value) {
		return new NetworkString(value);
	}

	private NetworkString(String value) {
		super(value, PacketByteBuf::writeString);
	}

	static {
		NetworkSupport.register(new Identifier("java", "string"), NetworkString.class, buf -> NetworkString.of(buf.readString()));
	}
}
