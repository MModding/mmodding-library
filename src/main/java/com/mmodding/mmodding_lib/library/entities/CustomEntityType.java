package com.mmodding.mmodding_lib.library.entities;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.ducks.QuiltEntityTypeBuilderDuckInterface;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.entity.impl.QuiltEntityType;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEntityType<T extends Entity> extends QuiltEntityType<T> implements EntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomEntityType(EntityFactory<T> factory, SpawnGroup spawnGroup, boolean saveable, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> spawnBlocks, EntityDimensions entityDimensions, int maxTrackDistance, int trackTickInterval, @Nullable Boolean alwaysUpdateVelocity) {
		super(factory, spawnGroup, saveable, summonable, fireImmune, spawnableFarFromPlayer, spawnBlocks, entityDimensions, maxTrackDistance, trackTickInterval, alwaysUpdateVelocity);
	}

	public static <T extends Entity> CustomEntityType<T> create(BuilderSetup<T> builderSetup) {
		return CustomEntityType.create(SpawnGroup.MISC, builderSetup);
	}

	public static <T extends Entity> CustomEntityType<T> create(@NotNull SpawnGroup spawnGroup, BuilderSetup<T> builderSetup) {
		return CustomEntityType.create(spawnGroup, (entityType, world) -> null, builderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> CustomEntityType<T> create(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, BuilderSetup<T> builderSetup) {
		return ((QuiltEntityTypeBuilderDuckInterface<T>) builderSetup.setup(QuiltEntityTypeBuilder.create(spawnGroup, factory))).mmodding_lib$buildCustom();
	}

	public static <T extends LivingEntity> CustomEntityType<T> createLiving(LivingBuilderSetup<T> livingBuilderSetup) {
		return CustomEntityType.createLiving(SpawnGroup.MISC, livingBuilderSetup);
	}

	public static <T extends LivingEntity> CustomEntityType<T> createLiving(@NotNull SpawnGroup spawnGroup, LivingBuilderSetup<T> livingBuilderSetup) {
		return CustomEntityType.createLiving(spawnGroup, (entityType, world) -> null, livingBuilderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends LivingEntity> CustomEntityType<T> createLiving(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, LivingBuilderSetup<T> livingBuilderSetup) {
		return ((QuiltEntityTypeBuilderDuckInterface<T>) livingBuilderSetup.setup(QuiltEntityTypeBuilder.createLiving().spawnGroup(spawnGroup).entityFactory(factory))).mmodding_lib$buildCustom();
	}

	public static <T extends MobEntity> CustomEntityType<T> createMob(MobBuilderSetup<T> mobBuilderSetup) {
		return CustomEntityType.createMob(SpawnGroup.MISC, mobBuilderSetup);
	}

	public static <T extends MobEntity> CustomEntityType<T> createMob(@NotNull SpawnGroup spawnGroup, MobBuilderSetup<T> mobBuilderSetup) {
		return CustomEntityType.createMob(spawnGroup, (entityType, world) -> null, mobBuilderSetup);
	}

	@SuppressWarnings("unchecked")
	public static <T extends MobEntity> CustomEntityType<T> createMob(@NotNull SpawnGroup spawnGroup, @NotNull EntityType.EntityFactory<T> factory, MobBuilderSetup<T> mobBuilderSetup) {
		return ((QuiltEntityTypeBuilderDuckInterface<T>) mobBuilderSetup.setup(QuiltEntityTypeBuilder.createMob().spawnGroup(spawnGroup).entityFactory(factory))).mmodding_lib$buildCustom();
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

		QuiltEntityTypeBuilder<T> setup(QuiltEntityTypeBuilder<T> builder);
	}

	@FunctionalInterface
	public interface LivingBuilderSetup<T extends LivingEntity> {

		QuiltEntityTypeBuilder.Living<T> setup(QuiltEntityTypeBuilder.Living<T> builder);
	}

	@FunctionalInterface
	public interface MobBuilderSetup<T extends MobEntity> {

		QuiltEntityTypeBuilder.Mob<T> setup(QuiltEntityTypeBuilder.Mob<T> builder);
	}
}
