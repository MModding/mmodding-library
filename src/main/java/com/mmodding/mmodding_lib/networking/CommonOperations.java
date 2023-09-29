package com.mmodding.mmodding_lib.networking;

import com.mmodding.mmodding_lib.library.events.networking.LivingEntityNetworkingEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.Map;

public class CommonOperations {

	public static void sendLivingEntityStuckArrowTypesToClient(LivingEntity livingEntity, Map<Integer, Identifier> stuckArrowTypes, ServerPlayerEntity player) {
		PacketByteBuf packet = PacketByteBufs.create();

		packet.writeVarInt(livingEntity.getId());
		packet.writeMap(stuckArrowTypes, PacketByteBuf::writeVarInt, PacketByteBuf::writeIdentifier);

		LivingEntityNetworkingEvents.BEFORE_STUCK_ARROW_TYPES.invoker().beforeStuckArrowTypesSent(livingEntity, stuckArrowTypes);

		ServerPlayNetworking.send(player, MModdingPackets.LIVING_ENTITY_STUCK_ARROW_TYPES, packet);

		LivingEntityNetworkingEvents.AFTER_STUCK_ARROW_TYPES.invoker().afterStuckArrowTypesSent(livingEntity, stuckArrowTypes);
	}
}
