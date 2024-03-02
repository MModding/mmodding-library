package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class NetworkItemStack extends NetworkPrimitive<ItemStack> implements NetworkSupport {

	public static NetworkItemStack of(ItemStack value) {
		return new NetworkItemStack(value);
	}

	private NetworkItemStack(ItemStack value) {
		super(value, PacketByteBuf::writeItemStack);
	}
}
