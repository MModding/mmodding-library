package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkShort extends NetworkPrimitive<Short> implements NetworkSupport {

	public static NetworkShort of(short value) {
		return new NetworkShort(value);
	}

	private NetworkShort(short value) {
		super(value, PacketByteBuf::writeShort);
	}

	static {
		NetworkSupport.register(new Identifier("java", "short"), NetworkShort.class, buf -> NetworkShort.of(buf.readShort()));
	}
}
