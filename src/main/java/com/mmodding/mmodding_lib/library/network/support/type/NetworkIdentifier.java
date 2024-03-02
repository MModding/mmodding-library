package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkIdentifier extends NetworkPrimitive<Identifier> implements NetworkSupport {

	public static NetworkIdentifier of(Identifier value) {
		return new NetworkIdentifier(value);
	}

	private NetworkIdentifier(Identifier value) {
		super(value, PacketByteBuf::writeIdentifier);
	}
}
