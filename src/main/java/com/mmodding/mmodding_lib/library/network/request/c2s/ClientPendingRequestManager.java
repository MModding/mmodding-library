package com.mmodding.mmodding_lib.library.network.request.c2s;

import com.mmodding.mmodding_lib.library.network.request.common.AbstractPendingRequestManager;
import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientPendingRequestManager<T extends NetworkSupport> extends AbstractPendingRequestManager<T> {

	private static final Map<Identifier, ClientPendingRequestManager<?>> PENDING_REQUESTS = new HashMap<>();

	public static Set<Identifier> getManagerKeys() {
		return ClientPendingRequestManager.PENDING_REQUESTS.keySet();
	}

	public static boolean exists(Identifier identifier) {
		return ClientPendingRequestManager.PENDING_REQUESTS.containsKey(identifier);
	}

	public static ClientPendingRequestManager<?> getManager(Identifier identifier) {
		return ClientPendingRequestManager.PENDING_REQUESTS.get(identifier);
	}

	public ClientPendingRequestManager(Identifier handler) {
		this(handler, 0);
	}

	public ClientPendingRequestManager(Identifier handler, int maximumEntryNumberForEachBuff) {
		super(handler, maximumEntryNumberForEachBuff);
		ClientPendingRequestManager.PENDING_REQUESTS.put(handler, this);
	}

	public void send() {
		this.provideBufs().forEach(buf -> ClientPlayNetworking.send(MModdingPackets.C2S_REQUESTS, buf));
	}
}
