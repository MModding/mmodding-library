package com.mmodding.library.network.impl.delay;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.list.MixedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class DelayedNetworkPackets {

	@Environment(EnvType.CLIENT)
	public record RequestPacket(UUID requestTracker, Identifier requestIdentifier, MixedList requestArguments) implements FabricPacket {

		public static final PacketType<RequestPacket> TYPE = PacketType.create(
			MModdingLibrary.createId("delayed_network/request"),
			RequestPacket::new
		);

		public RequestPacket(PacketByteBuf buf) {
			this(buf.readUuid(), buf.readIdentifier(), buf.readMixedList());
		}

		@Override
		public void write(PacketByteBuf buf) {
			buf.writeUuid(this.requestTracker);
			buf.writeIdentifier(this.requestIdentifier);
			buf.writeMixedList(this.requestArguments);
		}

		@Override
		public PacketType<RequestPacket> getType() {
			return RequestPacket.TYPE;
		}
	}

	public record ResponsePacket(UUID tracker, MixedList arguments) implements FabricPacket {

		public static final PacketType<ResponsePacket> TYPE = PacketType.create(
			MModdingLibrary.createId("delayed_network/response"),
			ResponsePacket::new
		);

		public ResponsePacket(PacketByteBuf buf) {
			this(buf.readUuid(), buf.readMixedList());
		}

		@Override
		public void write(PacketByteBuf buf) {
			buf.writeUuid(this.tracker);
			buf.writeMixedList(this.arguments);
		}

		@Override
		public PacketType<ResponsePacket> getType() {
			return ResponsePacket.TYPE;
		}
	}
}
