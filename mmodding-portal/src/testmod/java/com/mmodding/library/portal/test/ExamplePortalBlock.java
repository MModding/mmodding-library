package com.mmodding.library.portal.test;

import com.mmodding.library.math.api.AreaUtil;
import com.mmodding.library.math.api.Colliders;
import com.mmodding.library.portal.api.NodeBuildingPortal;
import com.mmodding.library.portal.api.catalog.DimensionScalingPortalBlock;
import com.mmodding.library.portal.api.util.PortalLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jspecify.annotations.Nullable;

public class ExamplePortalBlock extends DimensionScalingPortalBlock implements NodeBuildingPortal<ExamplePortalBlock.Context> {

	public ExamplePortalBlock(Properties properties) {
		super(Level.END, MModdingPortalTests.EXAMPLE_POI, 16, PortalLookup.SURFACE, properties);
	}

	@Override
	public Colliders createPortalFrameColliders(Context context) {
		return Colliders.box(new Vec3i(-1, -1, -1), new Vec3i(1, 1, 1));
	}

	@Override
	public void createPortal(ServerLevel level, BlockPos placementOrigin, Context context) {
		AreaUtil.forBlockPosInBox(placementOrigin.offset(-1, -1, -1), placementOrigin.offset(1, 1, 1), pos -> {
			level.setBlockAndUpdate(pos, pos.getY() - placementOrigin.getY() < 0 ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.AIR.defaultBlockState());
		});
		level.setBlockAndUpdate(placementOrigin.offset(1,0, 1), this.defaultBlockState());
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
			return newLevel.getPortalBinder().lookupOrBuildClosestForTransition(this, entity, portalEntryPos, new Context(), lookupOrigin, this.lookupRadius).orElse(null);
		}
		else {
			return null;
		}
	}

	public record Context() {}
}
