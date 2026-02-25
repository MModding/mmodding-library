package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.network.impl.delay.DelayedNetworkImpl;
import com.mmodding.library.network.impl.delay.DelayedNetworkPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class NetworkClientInitializer implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(DelayedNetworkPackets.ResponsePacket.TYPE, (packet, player, sender) -> {
			DelayedNetworkImpl.CLIENT_DELAYED_ACTIONS.get(packet.tracker()).handle(packet.arguments());
			DelayedNetworkImpl.CLIENT_DELAYED_ACTIONS.remove(packet.tracker());
		});
		ClientPlayNetworking.registerGlobalReceiver(DelayedNetworkPackets.RequestPacket.TYPE, (packet, player, sender) -> {
			MixedList arguments = DelayedNetworkImpl.CLIENT_REQUEST_PROCESSORS
				.get(packet.requestIdentifier())
				.process(packet.requestArguments());
			ClientPlayNetworking.send(new DelayedNetworkPackets.ResponsePacket(packet.requestTracker(), arguments));
		});
	}
}
