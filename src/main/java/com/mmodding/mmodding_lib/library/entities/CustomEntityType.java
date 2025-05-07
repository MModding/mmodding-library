package com.mmodding.mmodding_lib.library.entities;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.ducks.FabricOrQuiltEntityTypeBuilderDuckInterface;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends EntityType<T> implements EntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final Boolean alwaysUpdateVelocity;

	public CustomEntityType(EntityFactory<T> factory, SpawnGroup spawnGroup, boolean saveable, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> specificSpawnBlocks, EntityDimensions dimensions, int trackRange, int trackedUpdateRate, boolean forceTrackedVelocityUpdates) {
		super(factory, spawnGroup, saveable, summonable, fireImmune, spawnableFarFromPlayer, specificSpawnBlocks, dimensions, trackRange, trackedUpdateRate);
		this.alwaysUpdateVelocity = forceTrackedVelocityUpdates;
	}

	public static <T extends Entity> CustomEntityType<T> create(BuilderSetup<T> builderSetup) {
		return CustomEntityType.create(SpawnGroup.MISC, builderSetup);
	}

	public static <T extends Entity> CustomEntityType<T> create(@NotNull SpawnGroup spawnGroup, BuilderSetup<T> builderSetup) {
		return CustomEntityType.create(spawnGroup, (entityType, world) -> null, builderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> CustomEntityType<T> create(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, BuilderSetup<T> builderSetup) {
		return ((FabricOrQuiltEntityTypeBuilderDuckInterface<T>) builderSetup.setup(FabricEntityTypeBuilder.create(spawnGroup, factory))).mmodding_lib$buildCustom();
	}

	public static <T extends LivingEntity> CustomEntityType<T> createLiving(LivingBuilderSetup<T> livingBuilderSetup) {
		return CustomEntityType.createLiving(SpawnGroup.MISC, livingBuilderSetup);
	}

	public static <T extends LivingEntity> CustomEntityType<T> createLiving(@NotNull SpawnGroup spawnGroup, LivingBuilderSetup<T> livingBuilderSetup) {
		return CustomEntityType.createLiving(spawnGroup, (entityType, world) -> null, livingBuilderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends LivingEntity> CustomEntityType<T> createLiving(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, LivingBuilderSetup<T> livingBuilderSetup) {
		return ((FabricOrQuiltEntityTypeBuilderDuckInterface<T>) livingBuilderSetup.setup(FabricEntityTypeBuilder.createLiving().spawnGroup(spawnGroup).entityFactory(factory))).mmodding_lib$buildCustom();
	}

	public static <T extends MobEntity> CustomEntityType<T> createMob(MobBuilderSetup<T> mobBuilderSetup) {
		return CustomEntityType.createMob(SpawnGroup.MISC, mobBuilderSetup);
	}

	public static <T extends MobEntity> CustomEntityType<T> createMob(@NotNull SpawnGroup spawnGroup, MobBuilderSetup<T> mobBuilderSetup) {
		return CustomEntityType.createMob(spawnGroup, (entityType, world) -> null, mobBuilderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends MobEntity> CustomEntityType<T> createMob(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, MobBuilderSetup<T> mobBuilderSetup) {
		return ((FabricOrQuiltEntityTypeBuilderDuckInterface<T>) mobBuilderSetup.setup(FabricEntityTypeBuilder.createMob().spawnGroup(spawnGroup).entityFactory(factory))).mmodding_lib$buildCustom();
	}

	@Override
	public boolean alwaysUpdateVelocity() {
		return Objects.requireNonNullElseGet(this.alwaysUpdateVelocity, super::alwaysUpdateVelocity);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}

	@FunctionalInterface
	public interface BuilderSetup<T extends Entity> {

		FabricEntityTypeBuilder<T> setup(FabricEntityTypeBuilder<T> builder);
	}

	@FunctionalInterface
	public interface LivingBuilderSetup<T extends LivingEntity> {

		FabricEntityTypeBuilder.Living<T> setup(FabricEntityTypeBuilder.Living<T> builder);
	}

	@FunctionalInterface
	public interface MobBuilderSetup<T extends MobEntity> {

		FabricEntityTypeBuilder.Mob<T> setup(FabricEntityTypeBuilder.Mob<T> builder);
	}
}
