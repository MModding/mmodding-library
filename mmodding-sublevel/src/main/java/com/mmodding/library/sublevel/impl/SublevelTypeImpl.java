package com.mmodding.library.sublevel.impl;

import com.mmodding.library.java.api.function.Mapper;
import com.mmodding.library.sublevel.api.SublevelType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApiStatus.Internal
public class SublevelTypeImpl<A> implements SublevelType<A> {

	public static final Map<Identifier, SublevelTypeImpl<?>> TYPES = new Object2ObjectOpenHashMap<>();

	public static final Set<Identifier> ROOT_LEVEL_DISABLED = new HashSet<>();

	private final Mapper<A, String> mapper;
	private final ResourceKey<Level> level;
	private final ResourceKey<LevelStem> stem;
	private final int chunkSquareRadius;
	private final Map<String, ServerSublevel<?>> levels = new Object2ObjectOpenHashMap<>();

	public SublevelTypeImpl(Mapper<A, String> mapper, ResourceKey<LevelStem> stem, int chunkSquareRadius) {
		if (stem.equals(LevelStem.OVERWORLD) || stem.equals(LevelStem.NETHER) || stem.equals(LevelStem.END)) {
			throw new IllegalArgumentException("What? You should not reduce vanilla dimensions as sublevel roots.");
		}
		this.mapper = mapper;
		this.level = ResourceKey.create(Registries.DIMENSION, stem.identifier());
		this.stem = stem;
		this.chunkSquareRadius = chunkSquareRadius;
		ROOT_LEVEL_DISABLED.add(stem.identifier());
		TYPES.put(stem.identifier(), this);
	}

	@Override
	public Mapper<A, String> attachmentStringMapper() {
		return this.mapper;
	}

	@Override
	public ResourceKey<Level> dimension() {
		return this.level;
	}

	@Override
	public ResourceKey<LevelStem> stem() {
		return this.stem;
	}

	@Override
	public int chunkSquareRadius() {
		return this.chunkSquareRadius;
	}

	@Override
	public ServerLevel getOrCreate(ServerLevel parent, A attachment) {
		return this.getOrCreate(parent, this.mapper.map(attachment));
	}

	/**
	 * @implNote is used by {@link com.mmodding.library.sublevel.mixin.MinecraftServerMixin} to load sublevels when the world is loaded
	 */
	public ServerLevel getOrCreate(ServerLevel parent, String mappedAttachment) {
		return this.levels.computeIfAbsent(mappedAttachment, _ -> new ServerSublevel<>(this, mappedAttachment, parent));
	}

	public Collection<ServerSublevel<?>> levelValues() {
		return this.levels.values();
	}

	public void clearLevels() {
		this.levels.clear();
	}
}
