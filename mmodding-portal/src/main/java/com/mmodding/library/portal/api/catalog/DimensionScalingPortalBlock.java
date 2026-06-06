package com.mmodding.library.portal.api.catalog;

import com.mmodding.library.portal.api.NodeBindingPortal;
import com.mmodding.library.portal.api.util.PortalLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;

/**
 * A {@link NodeBindingPortal} dedicated to a specified dimension, which will look up for an associated
 * portal at the scaled position from original coordinates, and for a given radius, and will bind to it
 * if found. It does not create portals in situations where it does not find any destination portal.
 */
public class DimensionScalingPortalBlock extends Block implements NodeBindingPortal {

	protected final ResourceKey<Level> dimension;
	protected final ResourceKey<PoiType> pointOfInterest;
	protected final int lookupRadius;
	protected final PortalLookup portalLookup;

	public DimensionScalingPortalBlock(ResourceKey<Level> dimension, ResourceKey<PoiType> pointOfInterest, int lookupRadius, PortalLookup portalLookup, Properties properties) {
		super(properties);
		this.dimension = dimension;
		this.pointOfInterest = pointOfInterest;
		this.lookupRadius = lookupRadius;
		this.portalLookup = portalLookup;
	}

	@Override
	public ResourceKey<PoiType> pointOfInterest() {
		return this.pointOfInterest;
	}

	@Override
	public Comparator<BlockPos> closestSuitableComparator(LevelReader level, BlockPos lookupOrigin) {
		return this.portalLookup.provide(level, lookupOrigin);
	}

	@Override
	@Nullable
	public TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos) {
		ResourceKey<Level> newDimension = currentLevel.dimension() == this.dimension ? Level.OVERWORLD : this.dimension;
		ServerLevel newLevel = currentLevel.getServer().getLevel(newDimension);
		if (newLevel != null) {
			WorldBorder newWorldBorder = newLevel.getWorldBorder();
			double teleportationScale = DimensionType.getTeleportationScale(currentLevel.dimensionType(), newLevel.dimensionType());
			BlockPos lookupOrigin = newWorldBorder.clampToBounds(entity.getX() * teleportationScale, entity.getY(), entity.getZ() * teleportationScale);
			return newLevel.getPortalBinder().lookupClosestForTransition(this, entity, portalEntryPos, lookupOrigin, this.lookupRadius).orElse(null);
		}
		else {
			return null;
		}
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (entity.canUsePortal(false)) {
			entity.setAsInsidePortal(this, pos);
		}
	}

	@Override
	protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state, final boolean includeData) {
		return ItemStack.EMPTY;
	}
}
