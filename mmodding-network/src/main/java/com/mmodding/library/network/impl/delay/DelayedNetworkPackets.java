package com.mmodding.library.network.impl.delay;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.list.MixedList;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public class DelayedNetworkPackets {

	public record RequestPacket(UUID requestTracker, Identifier requestIdentifier, MixedList requestArguments) implements FabricPacket {

		public static final PacketType<RequestPacket> TYPE = PacketType.create(
			MModdingLibrary.createId("delayed_network/request"),
			RequestPacket::new
		);

		public RequestPacket(FriendlyByteBuf buf) {
			this(buf.readUUID(), buf.readIdentifier(), buf.readMixedList());
		}

		@Override
		public void write(FriendlyByteBuf buf) {
			buf.writeUUID(this.requestTracker);
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

		public ResponsePacket(FriendlyByteBuf buf) {
			this(buf.readUUID(), buf.readMixedList());
		}

		@Override
		public void write(FriendlyByteBuf buf) {
			buf.writeUUID(this.tracker);
			buf.writeMixedList(this.arguments);
		}

		@Override
		public PacketType<ResponsePacket> getType() {
			return ResponsePacket.TYPE;
		}
	}
}
