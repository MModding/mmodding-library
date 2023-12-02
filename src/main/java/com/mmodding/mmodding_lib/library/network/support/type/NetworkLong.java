package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkLong extends NetworkPrimitive<Long> implements NetworkSupport {

	public static NetworkLong of(long value) {
		return new NetworkLong(value);
	}

	private NetworkLong(long value) {
		super(value, PacketByteBuf::writeVarLong);
	}

	static {
		NetworkSupport.register(new Identifier("java", "long"), NetworkLong.class, buf -> NetworkLong.of(buf.readVarLong()));
	}
}
