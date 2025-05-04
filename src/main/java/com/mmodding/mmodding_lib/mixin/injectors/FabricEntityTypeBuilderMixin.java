package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.ducks.FabricEntityTypeBuilderDuckInterface;
import com.mmodding.mmodding_lib.library.entities.CustomEntityType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FabricEntityTypeBuilder.class, remap = false)
public class FabricEntityTypeBuilderMixin<T extends Entity> implements FabricEntityTypeBuilderDuckInterface<T> {

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
            this.trackingTickInterval
        );
    }

	@Mixin(value = FabricEntityTypeBuilder.Living.class, remap = false)
	public static class Living<T extends LivingEntity> extends FabricEntityTypeBuilderMixin<T> {

		@Shadow
		private DefaultAttributeContainer.Builder defaultAttributeBuilder;

		@Override
		public CustomEntityType<T> mmodding_lib$buildCustom() {
			final CustomEntityType<T> type = super.mmodding_lib$buildCustom();

			if (this.defaultAttributeBuilder != null) {
				DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(type, this.defaultAttributeBuilder.build());
			}

			return type;
		}
	}

	@Mixin(value = FabricEntityTypeBuilder.Mob.class, remap = false)
	public static class Mob<T extends MobEntity> extends Living<T> {

		@Shadow
		private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

		@Shadow
		private SpawnRestriction.Location restrictionLocation;

		@Shadow
		private Heightmap.Type restrictionHeightmap;

		@Override
		public CustomEntityType<T> mmodding_lib$buildCustom() {
			CustomEntityType<T> type = super.mmodding_lib$buildCustom();

			if (this.spawnPredicate != null) {
				SpawnRestriction.register(type, this.restrictionLocation, this.restrictionHeightmap, this.spawnPredicate);
			}

			return type;
		}
	}
}
