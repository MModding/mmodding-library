package com.mmodding.library.portal.impl.storage;

import com.mmodding.library.core.api.MModdingLibrary;
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
				BlockPos.CODEC,
				GlobalPos.CODEC
			)
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

	private void addToStorage(Set<BlockPos> checked, Block instance, ServerLevel originLevel, BlockPos sourcePos, ServerLevel destinationLevel, BlockPos destinationPos) {
		this.storage.computeIfAbsent(originLevel.dimension(), _ -> new Object2ObjectOpenHashMap<>()).put(sourcePos, new GlobalPos(destinationLevel.dimension(), destinationPos));
		checked.add(sourcePos);
		for (Direction direction : Direction.values()) {
			BlockPos relative = sourcePos.relative(direction);
			if (originLevel.getBlockState(relative).is(instance) && !checked.contains(relative)) {
				this.addToStorage(checked, instance, originLevel, relative, destinationLevel, destinationPos);
			}
		}
	}

	public BlockPos maybeCreateBound(ServerLevel sourceLevel, BlockPos sourcePos, ServerLevel newLevel, BlockPos newPos, boolean enabled) {
		if (enabled) {
			Block block = sourceLevel.getBlockState(sourcePos).getBlock();
			HashSet<BlockPos> cache = new HashSet<>();
			this.addToStorage(cache, block, sourceLevel, sourcePos, newLevel, newPos);
			cache.clear();
			this.addToStorage(cache, block, newLevel, newPos, sourceLevel, sourcePos);
			cache.clear();
		}
		return newPos;
	}

	public void removeBoundFrom(ServerLevel level, BlockPos pos) {
		this.storage.get(level.dimension()).remove(pos);
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
	}

	@Nullable
	public GlobalPos getPossibleNodeTarget(ServerLevel sourcePortalLevel, BlockPos sourcePortalPos) {
		return this.storage.getOrDefault(sourcePortalLevel.dimension(), Map.of()).getOrDefault(sourcePortalPos, null);
	}

	public interface Duck {

		PortalNodeStorage mmodding$getPortalNodeStorage();
	}
}
