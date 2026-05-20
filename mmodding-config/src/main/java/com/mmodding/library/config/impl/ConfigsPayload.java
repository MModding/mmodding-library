package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.Configs;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.core.api.MModdingLibrary;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Map;

public record ConfigsPayload(Map<Identifier, ConfigContent> contents) implements CustomPacketPayload {

	public static final Type<ConfigsPayload> TYPE = new Type<>(MModdingLibrary.createId("networking/configs"));
	public static final StreamCodec<FriendlyByteBuf, ConfigsPayload> CODEC = CustomPacketPayload.codec(ConfigsPayload::write, ConfigsPayload::new);

	public ConfigsPayload(FriendlyByteBuf buf) {
		Map<Identifier, ConfigContent> result = new Object2ObjectOpenHashMap<>();
		int amount = buf.readInt();
		for (int i = 0; i < amount; i++) {
			Identifier configId = buf.readIdentifier();
			Config config = Configs.get(configId);
			ConfigContent content = config.getStreamCodec().decode(buf);
			result.put(configId, content);
		}
		this(result);
	}

	private void write(FriendlyByteBuf buf) {
		buf.writeInt(this.contents.size());
		for (Map.Entry<Identifier, ConfigContent> entry : this.contents.entrySet()) {
			buf.writeIdentifier(entry.getKey());
			Config config = Configs.get(entry.getKey());
			config.getStreamCodec().encode(buf, config.getContent());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
