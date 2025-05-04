package com.mmodding.mmodding_lib.library.network.request.s2c;

import com.mmodding.mmodding_lib.library.network.request.common.AbstractPendingRequestManager;
import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServerPendingRequestManager<T extends NetworkSupport> extends AbstractPendingRequestManager<T> {

	private static final Map<Identifier, ServerPendingRequestManager<?>> PENDING_REQUESTS = new HashMap<>();

	public static Set<Identifier> getManagerKeys() {
		return ServerPendingRequestManager.PENDING_REQUESTS.keySet();
	}

	public static boolean exists(Identifier identifier) {
		return ServerPendingRequestManager.PENDING_REQUESTS.containsKey(identifier);
	}

	public static ServerPendingRequestManager<?> getManager(Identifier identifier) {
		return ServerPendingRequestManager.PENDING_REQUESTS.get(identifier);
	}

	public ServerPendingRequestManager(Identifier handler) {
		this(handler, 0);
	}

	public ServerPendingRequestManager(Identifier handler, int maximumEntryNumberForEachBuff) {
		super(handler, maximumEntryNumberForEachBuff);
		ServerPendingRequestManager.PENDING_REQUESTS.put(handler, this);
	}

	public void send(ServerPlayerEntity player) {
		this.provideBufs().forEach(buf -> ServerPlayNetworking.send(player, MModdingPackets.S2C_REQUESTS, buf));
	}
}
