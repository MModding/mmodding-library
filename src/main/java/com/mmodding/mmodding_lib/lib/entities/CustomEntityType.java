package com.mmodding.mmodding_lib.lib.entities;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.impl.object.builder.FabricEntityType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends FabricEntityType<T> implements EntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomEntityType(EntityFactory<T> factory, SpawnGroup spawnGroup, boolean bl, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> spawnBlocks, EntityDimensions entityDimensions, int maxTrackDistance, int trackTickInterval, Boolean alwaysUpdateVelocity) {
		super(factory, spawnGroup, bl, summonable, fireImmune, spawnableFarFromPlayer, spawnBlocks, entityDimensions, maxTrackDistance, trackTickInterval, alwaysUpdateVelocity);
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
