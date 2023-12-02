package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkDouble extends NetworkPrimitive<Double> implements NetworkSupport {

	public static NetworkDouble of(double value) {
		return new NetworkDouble(value);
	}

	private NetworkDouble(double value) {
		super(value, PacketByteBuf::writeDouble);
	}

	static {
		NetworkSupport.register(new Identifier("java", "double"), NetworkDouble.class, buf -> NetworkDouble.of(buf.readDouble()));
	}
}
