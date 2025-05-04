package com.mmodding.mmodding_lib.library.network.request.common;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkList;
import com.mmodding.mmodding_lib.library.utils.BiHashMap;
import com.mmodding.mmodding_lib.library.utils.BiMap;
import com.mmodding.mmodding_lib.library.utils.MapUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.BooleanSupplier;

public abstract class AbstractPendingRequestManager<T extends NetworkSupport> {

	protected final Identifier handler;
	protected final BiMap<UUID, NetworkList, RequestAction<T>> primaries;
	protected final Map<UUID, NetworkList> pending;
	protected final Map<UUID, RequestAction<T>> actions;
	protected final int maximumEntryNumberForEachBuff;

	protected AbstractPendingRequestManager(Identifier handler, int maximumEntryNumberForEachBuff) {
		this.handler = handler;
		this.primaries = new BiHashMap<>();
		this.pending = new HashMap<>();
		this.actions = new HashMap<>();
		this.maximumEntryNumberForEachBuff = maximumEntryNumberForEachBuff;
	}

	public void primary(NetworkList arguments, RequestAction<T> action) {
		UUID uuid = MapUtils.untilNotContainingKey(UUID::randomUUID, this.primaries, this.pending, this.actions);
		this.primaries.put(uuid, arguments, action);
	}

	public void action(NetworkList arguments, RequestAction<T> action) {
		UUID uuid = MapUtils.untilNotContainingKey(UUID::randomUUID, this.primaries, this.pending, this.actions);
		this.pending.put(uuid, arguments);
		this.actions.put(uuid, action);
	}

	public void primaryOrAction(NetworkList arguments, RequestAction<T> action, BooleanSupplier isAction) {
		if (!isAction.getAsBoolean()) {
			this.primary(arguments, action);
		}
		else {
			this.action(arguments, action);
		}
	}

	public void actionOrPrimary(NetworkList arguments, RequestAction<T> action, BooleanSupplier isPrimary) {
		if (!isPrimary.getAsBoolean()) {
			this.action(arguments, action);
		}
		else {
			this.primary(arguments, action);
		}
	}

	public Optional<RequestAction<T>> consume(UUID uuid) {
		RequestAction<T> action;
		if (this.primaries.containsKey(uuid)) {
			action = this.primaries.getSecondValue(uuid);
		}
		else {
			this.pending.remove(uuid);
			action = MapUtils.consume(this.actions, uuid);
		}
		return Optional.ofNullable(action);
	}

	public void clear() {
		this.pending.clear();
		this.actions.clear();
	}

	protected List<PacketByteBuf> provideBufs() {
		List<PacketByteBuf> bufs = new ArrayList<>();
		Map<UUID, NetworkList> provided = new HashMap<>();
		this.primaries.forEachFirst(provided::put);
		provided.putAll(this.pending);
		MapUtils.divide(provided, this.maximumEntryNumberForEachBuff).forEach(
			map -> {
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeIdentifier(this.handler);
				buf.writeMap(map, PacketByteBuf::writeUuid, (current, args) -> args.writeComplete(current));
				bufs.add(buf);
			}
		);
		return bufs;
	}
}
