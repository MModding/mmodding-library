package com.mmodding.mmodding_lib.library.entities.data;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.TrackedDataHandlerUtils;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.networking.api.tracked_data.QuiltTrackedDataHandlerRegistry;

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
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("boolean_list"), MModdingTrackedDataHandlers.BOOLEAN_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("integer_list"), MModdingTrackedDataHandlers.INTEGER_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("long_list"), MModdingTrackedDataHandlers.LONG_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("float_list"), MModdingTrackedDataHandlers.FLOAT_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("double_list"), MModdingTrackedDataHandlers.DOUBLE_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("string_list"), MModdingTrackedDataHandlers.STRING_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("identifier_list"), MModdingTrackedDataHandlers.IDENTIFIER_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("uuid_list"), MModdingTrackedDataHandlers.UUID_LIST);
	}
}
