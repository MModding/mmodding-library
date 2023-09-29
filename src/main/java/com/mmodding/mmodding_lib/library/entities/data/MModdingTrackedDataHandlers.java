package com.mmodding.mmodding_lib.library.entities.data;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.TrackedDataHandlerUtils;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.networking.api.tracked_data.QuiltTrackedDataHandlerRegistry;

import java.util.List;

public class MModdingTrackedDataHandlers {

	public static final TrackedDataHandler<List<String>> STRING_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeString, PacketByteBuf::readString);
	public static final TrackedDataHandler<List<Identifier>> IDENTIFIER_LIST = TrackedDataHandlerUtils.createTrackedDataListHandler(PacketByteBuf::writeIdentifier, PacketByteBuf::readIdentifier);

	static {
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("string_list"), MModdingTrackedDataHandlers.STRING_LIST);
		QuiltTrackedDataHandlerRegistry.register(new MModdingIdentifier("identifier_list"), MModdingTrackedDataHandlers.IDENTIFIER_LIST);
	}
}
