package com.mmodding.library.network.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.network.api.NetworkHandlers;
import com.mmodding.library.network.impl.delay.DelayedNetworkImpl;
import com.mmodding.library.network.impl.delay.DelayedNetworkPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import java.util.UUID;

public class NetworkInitializer implements ModInitializer {

	private static Identifier java(String path) {
		return Identifier.fromNamespaceAndPath("java", path);
	}

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.serverboundConfiguration().register(DelayedNetworkPackets.RequestPayload.TYPE, DelayedNetworkPackets.RequestPayload.CODEC);
		PayloadTypeRegistry.clientboundConfiguration().register(DelayedNetworkPackets.RequestPayload.TYPE, DelayedNetworkPackets.RequestPayload.CODEC);
		PayloadTypeRegistry.serverboundConfiguration().register(DelayedNetworkPackets.ResponsePayload.TYPE, DelayedNetworkPackets.ResponsePayload.CODEC);
		PayloadTypeRegistry.clientboundConfiguration().register(DelayedNetworkPackets.ResponsePayload.TYPE, DelayedNetworkPackets.ResponsePayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(DelayedNetworkPackets.RequestPayload.TYPE, DelayedNetworkPackets.RequestPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(DelayedNetworkPackets.RequestPayload.TYPE, DelayedNetworkPackets.RequestPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(DelayedNetworkPackets.ResponsePayload.TYPE, DelayedNetworkPackets.ResponsePayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(DelayedNetworkPackets.ResponsePayload.TYPE, DelayedNetworkPackets.ResponsePayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(DelayedNetworkPackets.RequestPayload.TYPE, (packet, context) -> {
			MixedList arguments = DelayedNetworkImpl.SERVER_REQUEST_PROCESSORS
				.get(packet.requestIdentifier())
				.process(context.player(), packet.requestArguments());
			ServerPlayNetworking.send(context.player(), new DelayedNetworkPackets.ResponsePayload(packet.requestTracker(), arguments));
		});
		ServerPlayNetworking.registerGlobalReceiver(DelayedNetworkPackets.ResponsePayload.TYPE, (packet, context) -> {
			DelayedNetworkImpl.SERVER_DELAYED_ACTIONS.get(packet.tracker()).handle(context.player(), packet.arguments());
			DelayedNetworkImpl.SERVER_DELAYED_ACTIONS.remove(packet.tracker());
		});
	}

	static {
		NetworkHandlers.register(Boolean.class, NetworkInitializer.java("boolean"), FriendlyByteBuf::readBoolean, FriendlyByteBuf::writeBoolean);
		NetworkHandlers.register(Byte.class, NetworkInitializer.java("byte"), FriendlyByteBuf::readByte, (buf, value) -> buf.writeByte(value));
		NetworkHandlers.register(byte[].class, NetworkInitializer.java("byte_array"), input -> input.readByteArray(), (output, value) -> output.writeByteArray(value));
		NetworkHandlers.register(Double.class, NetworkInitializer.java("double"), FriendlyByteBuf::readDouble, FriendlyByteBuf::writeDouble);
		NetworkHandlers.register(Float.class, NetworkInitializer.java("float"), FriendlyByteBuf::readFloat, FriendlyByteBuf::writeFloat);
		NetworkHandlers.register(Integer.class, NetworkInitializer.java("integer"), FriendlyByteBuf::readVarInt, FriendlyByteBuf::writeVarInt);
		NetworkHandlers.register(int[].class, NetworkInitializer.java("integer_array"), FriendlyByteBuf::readVarIntArray, FriendlyByteBuf::writeVarIntArray);
		NetworkHandlers.register(Long.class, NetworkInitializer.java("long"), FriendlyByteBuf::readVarLong, FriendlyByteBuf::writeVarLong);
		NetworkHandlers.register(long[].class, NetworkInitializer.java("long_array"), input -> input.readLongArray(), (output, value) -> output.writeLongArray(value));
		NetworkHandlers.register(Short.class, NetworkInitializer.java("short"), FriendlyByteBuf::readShort, (buf, value) -> buf.writeShort(value));
		NetworkHandlers.register(String.class, NetworkInitializer.java("string"), FriendlyByteBuf::readUtf, FriendlyByteBuf::writeUtf);
		NetworkHandlers.register(UUID.class, NetworkInitializer.java("uuid"), input -> input.readUUID(), (output, value) -> output.writeUUID(value));
		NetworkHandlers.register(Identifier.class, Identifier.withDefaultNamespace("identifier"), FriendlyByteBuf::readIdentifier, FriendlyByteBuf::writeIdentifier);
		NetworkHandlers.register(MixedList.class, MModdingLibrary.createId("list"), FriendlyByteBuf::readMixedList, FriendlyByteBuf::writeMixedList);
		NetworkHandlers.register(
			MixedMap.class,
			MModdingLibrary.createId("map"),
			buf -> buf.readMixedMap(current -> current.readByHandling(current.peekNextType().orElseThrow())),
			(buf, map) -> buf.writeMixedMap((MixedMap<?>) map, FriendlyByteBuf::writeByHandling)
		);
	}
}
