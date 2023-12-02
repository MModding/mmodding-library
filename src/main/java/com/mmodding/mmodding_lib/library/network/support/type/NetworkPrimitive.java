package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.network.PacketByteBuf;

public abstract class NetworkPrimitive<T> implements NetworkSupport {

	protected final T value;
	protected final PacketByteBuf.Writer<T> writer;

	public static <T> NetworkPrimitive<T> empty() {
		return new NetworkNull<>();
	}

	NetworkPrimitive(T value, PacketByteBuf.Writer<T> writer) {
		this.value = value;
		this.writer = writer;
	}

	@Override
	public void write(PacketByteBuf buf) {
		this.writer.accept(buf, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NetworkPrimitive<?> primitive) {
			return this.getValue() == primitive.getValue();
		}
		else {
			return super.equals(obj);
		}
	}

	@Override
	public String toString() {
		return this.getClass() + ";" + this.value.toString();
	}

	public T getValue() {
		return this.value;
	}
}
