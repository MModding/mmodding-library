package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.network.api.DynamicTypeSupportStreamCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;

public class MixedListStreamCodec<B extends ByteBuf> extends DynamicTypeSupportStreamCodec<B, MixedList> {

	public MixedListStreamCodec(Map<Class<?>, StreamCodec<B, Object>> supportedTypes) {
		super(supportedTypes);
	}

	@Override
	public MixedList decode(B input) {
		MixedList res = MixedList.create();
		int size = input.readInt();
		for (int i = 0; i < size; i++) {
			res.add(this.decodeTyped(input, "Received an unknown type through a Mixed List Codec at index " + i));
		}
		return res;
	}

	@Override
	public void encode(B output, MixedList value) {
		output.writeInt(value.size());
		value.forEach(typed -> this.encodeTyped(output, typed));
	}
}
