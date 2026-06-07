package com.mmodding.library.portal.impl.storage;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.math.api.Colliders;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PortalNodeStorage extends SavedData {

	public static final SavedDataType<PortalNodeStorage> TYPE = new SavedDataType<>(
		MModdingLibrary.createId("portal_nodes"),
		PortalNodeStorage::new,
		Codec.unboundedMap(
			ResourceKey.codec(Registries.DIMENSION),
			Codec.unboundedMap(
				MModdingCodecs.STRING_BLOCKPOS,
				GlobalPos.CODEC
			).xmap(m -> (Map<BlockPos, GlobalPos>) new Object2ObjectOpenHashMap<>(m), m -> m)
		).xmap(PortalNodeStorage::new, PortalNodeStorage::storage),
		null
	);

	private final Map<ResourceKey<Level>, Map<BlockPos, GlobalPos>> storage;

	private PortalNodeStorage() {
		this.storage = new Object2ObjectOpenHashMap<>();
	}

	private PortalNodeStorage(Map<ResourceKey<Level>, Map<BlockPos, GlobalPos>> storage) {
		this.storage = new Object2ObjectOpenHashMap<>(storage);
	}

	private Map<ResourceKey<Level>, Map<BlockPos, GlobalPos>> storage() {
		return this.storage;
	}

	// adding teleportation source and matching neighbors as portal binders to the storage -> it relies on portals to be stacked together
	private void addSourceToStorage(Set<BlockPos> checked, Block instance, ServerLevel originLevel, BlockPos sourcePos, ServerLevel destinationLevel, BlockPos destinationPos) {
		this.storage.computeIfAbsent(originLevel.dimension(), _ -> new Object2ObjectOpenHashMap<>()).put(sourcePos, new GlobalPos(destinationLevel.dimension(), destinationPos));
		checked.add(sourcePos);
		for (Direction direction : Direction.values()) {
			BlockPos relative = sourcePos.relative(direction);
			if (originLevel.getBlockState(relative).is(instance) && !checked.contains(relative)) {
				this.addSourceToStorage(checked, instance, originLevel, relative, destinationLevel, destinationPos);
			}
		}
	}

	// going through the built portal frame to find portal binders and storage the node for those
	private void addBuiltSourceToStorage(Block instance, ServerLevel originLevel, BlockPos sourcePos, ServerLevel destinationLevel, BlockPos destinationPos, Colliders colliders) {
		colliders.collisions().stream()
			.map(destinationPos::offset)
			.forEach(pos -> {
				if (destinationLevel.getBlockState(pos).is(instance)) {
					this.storage.computeIfAbsent(destinationLevel.dimension(), _ -> new Object2ObjectOpenHashMap<>()).put(pos, new GlobalPos(originLevel.dimension(), sourcePos));
				}
			});
	}

	public BlockPos maybeBindLookup(ServerLevel sourceLevel, BlockPos sourcePos, ServerLevel newLevel, BlockPos newPos, boolean enabled) {
		if (enabled) {
			Block portalInstance = sourceLevel.getBlockState(sourcePos).getBlock();
			HashSet<BlockPos> cache = new HashSet<>();
			this.addSourceToStorage(cache, portalInstance, sourceLevel, sourcePos, newLevel, newPos);
			cache.clear();
			this.addSourceToStorage(cache, portalInstance, newLevel, newPos, sourceLevel, sourcePos);
			cache.clear();
			this.setDirty();
		}
		return newPos;
	}

	public BlockPos maybeBindBuilt(ServerLevel sourceLevel, BlockPos sourcePos, ServerLevel newLevel, BlockPos newPos, Colliders portalFrameColliders, boolean enabled) {
		if (enabled) {
			Block portalInstance = sourceLevel.getBlockState(sourcePos).getBlock();
			HashSet<BlockPos> cache = new HashSet<>();
			this.addSourceToStorage(cache, portalInstance, sourceLevel, sourcePos, newLevel, newPos);
			cache.clear();
			this.addBuiltSourceToStorage(portalInstance, sourceLevel, sourcePos, newLevel, newPos, portalFrameColliders);
			this.setDirty();
		}
		return newPos;
	}

	public void removeBoundFrom(ServerLevel level, BlockPos pos) {
		Map<BlockPos, GlobalPos> bounds = this.storage.get(level.dimension());
		if (bounds != null) {
			bounds.remove(pos);
			Set<ResourceKey<Level>> pendingDeletion = new HashSet<>();
			if (this.storage.get(level.dimension()).isEmpty()) {
				pendingDeletion.add(level.dimension());
			}
			this.storage.forEach((key, map) -> {
				map.entrySet().removeIf(e -> e.getValue().equals(new GlobalPos(level.dimension(), pos)));
				if (this.storage.get(key).isEmpty()) {
					pendingDeletion.add(key);
				}
			});
			pendingDeletion.forEach(this.storage::remove);
			pendingDeletion.clear();
			this.setDirty();
		}
	}

	@Nullable
	public GlobalPos getPossibleNodeTarget(ServerLevel sourcePortalLevel, BlockPos sourcePortalPos) {
		return this.storage.getOrDefault(sourcePortalLevel.dimension(), Map.of()).getOrDefault(sourcePortalPos, null);
	}

	public interface Duck {

		PortalNodeStorage mmodding$getPortalNodeStorage();
	}
}
