package com.mmodding.mmodding_lib.library.entities.data;

import com.mmodding.mmodding_lib.library.utils.TrackedDataHandlerUtils;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public class MModdingTrackedDataHandlers {

	public static final TrackedDataHandler<List<Boolean>> BOOLEAN_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeBoolean, PacketByteBuf::readBoolean);
	public static final TrackedDataHandler<List<Integer>> INTEGER_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeVarInt, PacketByteBuf::readVarInt);
	public static final TrackedDataHandler<List<Long>> LONG_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeVarLong, PacketByteBuf::readVarLong);
	public static final TrackedDataHandler<List<Float>> FLOAT_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeFloat, PacketByteBuf::readFloat);
	public static final TrackedDataHandler<List<Double>> DOUBLE_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeDouble, PacketByteBuf::readDouble);
	public static final TrackedDataHandler<List<String>> STRING_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeString, PacketByteBuf::readString);
	public static final TrackedDataHandler<List<Identifier>> IDENTIFIER_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeIdentifier, PacketByteBuf::readIdentifier);
	public static final TrackedDataHandler<List<UUID>> UUID_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeUuid, PacketByteBuf::readUuid);

	static {
		// Looking forward to https://github.com/FabricMC/fabric/pull/4599
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.BOOLEAN_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.INTEGER_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.LONG_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.FLOAT_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.DOUBLE_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.STRING_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.IDENTIFIER_LIST);
		TrackedDataHandlerRegistry.register(MModdingTrackedDataHandlers.UUID_LIST);
	}
}
