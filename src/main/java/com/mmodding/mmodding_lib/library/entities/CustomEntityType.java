package com.mmodding.mmodding_lib.library.entities;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.ducks.QuiltEntityTypeBuilderDuckInterface;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.entity.impl.QuiltEntityType;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends QuiltEntityType<T> implements EntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomEntityType(EntityFactory<T> factory, SpawnGroup spawnGroup, boolean saveable, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> spawnBlocks, EntityDimensions entityDimensions, int maxTrackDistance, int trackTickInterval, @Nullable Boolean alwaysUpdateVelocity) {
		super(factory, spawnGroup, saveable, summonable, fireImmune, spawnableFarFromPlayer, spawnBlocks, entityDimensions, maxTrackDistance, trackTickInterval, alwaysUpdateVelocity);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> CustomEntityType<T> create(QuiltEntityTypeBuilder<T> builder) {
		return ((QuiltEntityTypeBuilderDuckInterface<T>) builder).mmodding_lib$buildCustom();
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
