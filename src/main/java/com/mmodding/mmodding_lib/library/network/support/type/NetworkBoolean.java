package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkBoolean extends NetworkPrimitive<Boolean> implements NetworkSupport {

	public static NetworkBoolean of(Boolean value) {
		return new NetworkBoolean(value);
	}

	private NetworkBoolean(Boolean value) {
		super(value, PacketByteBuf::writeBoolean);
	}

	static {
		NetworkSupport.register(new Identifier("java", "boolean"), NetworkBoolean.class, buf -> NetworkBoolean.of(buf.readBoolean()));
	}
}
