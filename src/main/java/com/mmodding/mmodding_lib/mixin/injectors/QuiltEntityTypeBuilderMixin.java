package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.ducks.QuiltEntityTypeBuilderDuckInterface;
import com.mmodding.mmodding_lib.library.entities.CustomEntityType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = QuiltEntityTypeBuilder.class, remap = false)
public class QuiltEntityTypeBuilderMixin<T extends Entity> implements QuiltEntityTypeBuilderDuckInterface<T> {

    @Shadow
    private EntityType.EntityFactory<T> factory;

    @Shadow
    @NotNull
    private SpawnGroup spawnGroup;

    @Shadow
    private boolean saveable;

    @Shadow
    private boolean summonable;

    @Shadow
    private boolean fireImmune;

    @Shadow
    private boolean spawnableFarFromPlayer;

    @Shadow
    private ImmutableSet<Block> canSpawnInside;

    @Shadow
    private EntityDimensions dimensions;

    @Shadow
    private int maxTrackingRange;

    @Shadow
    private int trackingTickInterval;

    @Shadow
    private Boolean alwaysUpdateVelocity;

    @Override
    public CustomEntityType<T> mmodding_lib$buildCustom() {
        return new CustomEntityType<>(
            this.factory,
            this.spawnGroup,
            this.saveable,
            this.summonable,
            this.fireImmune,
            this.spawnableFarFromPlayer,
            this.canSpawnInside,
            this.dimensions,
            this.maxTrackingRange,
            this.trackingTickInterval,
            this.alwaysUpdateVelocity
        );
    }
}
