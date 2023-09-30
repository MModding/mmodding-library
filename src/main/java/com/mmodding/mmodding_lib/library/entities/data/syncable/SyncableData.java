package com.mmodding.mmodding_lib.library.entities.data.syncable;

import com.mmodding.mmodding_lib.networking.MModdingPackets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SyncableData<V> extends AtomicReference<V> {

	private final Entity entity;
	private final Identifier identifier;
	private final TrackedDataHandler<V> handler;

	public SyncableData(V initialValue, Entity entity, Identifier identifier, TrackedDataHandler<V> handler) {
		super(initialValue);
		this.entity = entity;
		this.identifier = identifier;
		this.handler = handler;
		this.entity.getSyncableDataRegistry().put(this.identifier, this);
	}

	public SyncableData(Entity entity, Identifier identifier, TrackedDataHandler<V> handler) {
		super();
		this.entity = entity;
		this.identifier = identifier;
		this.handler = handler;
		this.entity.getSyncableDataRegistry().put(this.identifier, this);
	}

	public void synchronize() {
		World world = this.entity.getWorld();
		if (!world.isClient()) {
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeVarInt(this.entity.getId());
			buf.writeIdentifier(this.identifier);
			this.handler.write(buf, this.get());
			this.entity.getWorld().getPlayers().forEach(playerEntity -> ServerPlayNetworking.send(
				(ServerPlayerEntity) playerEntity, MModdingPackets.SYNCABLE_DATA, buf
			));
		}
	}

	public void accept(PacketByteBuf buf) {
		this.set(this.handler.read(buf));
	}

	@ApiStatus.Internal
	public static class Registry extends HashMap<Identifier, SyncableData<?>> implements Map<Identifier, SyncableData<?>> {

		private final Entity syncedEntity;

		public Registry(Entity syncedEntity) {
			this.syncedEntity = syncedEntity;
		}

		public void accept(PacketByteBuf buf) {
			World world = this.syncedEntity.getWorld();
			if (world.isClient()) {
				Identifier identifier = buf.readIdentifier();
				this.get(identifier).accept(buf);
			}
		}
	}
}
