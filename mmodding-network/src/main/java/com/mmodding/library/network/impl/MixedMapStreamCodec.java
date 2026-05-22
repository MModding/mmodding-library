package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;
import com.mmodding.library.network.api.DynamicTypeSupportStreamCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;

public class MixedMapStreamCodec<B extends ByteBuf, K> extends DynamicTypeSupportStreamCodec<B, MixedMap<K>> {

	private final StreamCodec<B, K> keyCodec;

	public MixedMapStreamCodec(StreamCodec<B, K> keyCodec, Map<Class<?>, StreamCodec<B, Object>> supportedTypes) {
		super(supportedTypes);
		this.keyCodec = keyCodec;
	}

	@Override
	public MixedMap<K> decode(B input) {
		MixedMap<K> res = input.readBoolean() ? MixedMap.linked() : MixedMap.create();
		int size = input.readInt();
		for (int i = 0; i < size; i++) {
			K key = this.keyCodec.decode(input);
			res.put(key, this.decodeTyped(input, "Received an unknown type through a Mixed Map Codec for property: " + key));
		}
		return res;
	}

	@Override
	public void encode(B output, MixedMap<K> value) {
		int size = value.size();
		output.writeBoolean(value instanceof LinkedMixedMapImpl<K>);
		output.writeInt(size);
		for (Map.Entry<K, Typed<?>> entry : value.entrySet()) {
			this.keyCodec.encode(output, entry.getKey());
			this.encodeTyped(output, entry.getValue());
		}
	}
}
