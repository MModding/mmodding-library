package com.mmodding.library.network.impl.delay;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.list.MixedList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public class DelayedNetworkPackets {

	public record RequestPayload(UUID requestTracker, Identifier requestIdentifier, MixedList requestArguments) implements CustomPacketPayload {

		public static final Type<RequestPayload> TYPE = new Type<>(MModdingLibrary.createId("delayed_network/request"));
		public static final StreamCodec<FriendlyByteBuf, RequestPayload> CODEC = CustomPacketPayload.codec(RequestPayload::write, RequestPayload::new);

		public RequestPayload(FriendlyByteBuf buf) {
			this(buf.readUUID(), buf.readIdentifier(), buf.readMixedList());
		}

		public void write(FriendlyByteBuf buf) {
			buf.writeUUID(this.requestTracker);
			buf.writeIdentifier(this.requestIdentifier);
			buf.writeMixedList(this.requestArguments);
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return RequestPayload.TYPE;
		}
	}

	public record ResponsePayload(UUID tracker, MixedList arguments) implements CustomPacketPayload {

		public static final Type<ResponsePayload> TYPE = new Type<>(MModdingLibrary.createId("delayed_network/response"));
		public static final StreamCodec<FriendlyByteBuf, ResponsePayload> CODEC = CustomPacketPayload.codec(ResponsePayload::write, ResponsePayload::new);

		public ResponsePayload(FriendlyByteBuf buf) {
			this(buf.readUUID(), buf.readMixedList());
		}

		public void write(FriendlyByteBuf buf) {
			buf.writeUUID(this.tracker);
			buf.writeMixedList(this.arguments);
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ResponsePayload.TYPE;
		}
	}
}
