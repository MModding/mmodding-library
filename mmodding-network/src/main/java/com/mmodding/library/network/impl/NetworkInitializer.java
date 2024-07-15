package com.mmodding.library.network.impl;

import com.mmodding.library.network.api.NetworkHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class NetworkInitializer implements ModInitializer {

	private static Identifier java(String path) {
		return new Identifier("java", path);
	}

	@Override
	public void onInitialize() {}

	static {
		NetworkHandlers.register(Boolean.class, NetworkInitializer.java("boolean"), PacketByteBuf::readBoolean, PacketByteBuf::writeBoolean);
		NetworkHandlers.register(Byte.class, NetworkInitializer.java("byte"), PacketByteBuf::readByte, (buf, value) -> buf.writeByte(value));
		NetworkHandlers.register(byte[].class, NetworkInitializer.java("byte_array"), PacketByteBuf::readByteArray, PacketByteBuf::writeByteArray);
		NetworkHandlers.register(Double.class, NetworkInitializer.java("double"), PacketByteBuf::readDouble, PacketByteBuf::writeDouble);
		NetworkHandlers.register(Float.class, NetworkInitializer.java("float"), PacketByteBuf::readFloat, PacketByteBuf::writeFloat);
		NetworkHandlers.register(Identifier.class, new Identifier("identifier"), PacketByteBuf::readIdentifier, PacketByteBuf::writeIdentifier);
		NetworkHandlers.register(Integer.class, NetworkInitializer.java("integer"), PacketByteBuf::readVarInt, PacketByteBuf::writeVarInt);
		NetworkHandlers.register(int[].class, NetworkInitializer.java("integer_array"), PacketByteBuf::readIntArray, PacketByteBuf::writeIntArray);
		NetworkHandlers.register(ItemStack.class, new Identifier("itemstack"), PacketByteBuf::readItemStack, PacketByteBuf::writeItemStack);
		NetworkHandlers.register(Long.class, NetworkInitializer.java("long"), PacketByteBuf::readVarLong, PacketByteBuf::writeVarLong);
		NetworkHandlers.register(long[].class, NetworkInitializer.java("long_array"), PacketByteBuf::readLongArray, PacketByteBuf::writeLongArray);
		NetworkHandlers.register(Short.class, NetworkInitializer.java("short"), PacketByteBuf::readShort, (buf, value) -> buf.writeShort(value));
		NetworkHandlers.register(String.class, NetworkInitializer.java("string"), PacketByteBuf::readString, PacketByteBuf::writeString);
		NetworkHandlers.register(UUID.class, NetworkInitializer.java("uuid"), PacketByteBuf::readUuid, PacketByteBuf::writeUuid);
	}
}
