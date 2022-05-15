package com.mmodding.mmodding_lib.lib.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends EntityType<T> implements EntityTypeRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomEntityType(EntityFactory<T> entityFactory, SpawnGroup spawnGroup, boolean bl, boolean bl2, boolean bl3, boolean bl4, ImmutableSet<Block> immutableSet, EntityDimensions entityDimensions, int i, int j) {
		super(entityFactory, spawnGroup, bl, bl2, bl3, bl4, immutableSet, entityDimensions, i, j);
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
