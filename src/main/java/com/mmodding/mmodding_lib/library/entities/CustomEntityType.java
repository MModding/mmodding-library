package com.mmodding.mmodding_lib.library.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends EntityType<T> implements EntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomEntityType(EntityFactory<T> factory, SpawnGroup spawnGroup, boolean bl, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> spawnBlocks, EntityDimensions entityDimensions, int maxTrackDistance, int trackTickInterval) {
		super(factory, spawnGroup, bl, summonable, fireImmune, spawnableFarFromPlayer, spawnBlocks, entityDimensions, maxTrackDistance, trackTickInterval);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
