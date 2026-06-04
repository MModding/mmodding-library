package com.mmodding.library.portal.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Comparator;

@FunctionalInterface
public interface PortalLookup {

	/**
	 * Prioritizes closest positions on the xz plane.
	 */
	PortalLookup AROUND_ORIGIN = (level, lookupOrigin) -> Comparator.<BlockPos>comparingDouble(pos -> pos.distSqr(lookupOrigin));

	/**
	 * Prioritizes closest positions on the xz plane that are deeper.
	 */
	PortalLookup DEPTH = (level, lookupOrigin) -> AROUND_ORIGIN.provide(level, lookupOrigin).thenComparingInt(Vec3i::getY);

	/**
	 * Prioritizes closest positions on the xz plane that are near the surface.
	 */
	PortalLookup SURFACE = (level, lookupOrigin) -> AROUND_ORIGIN.provide(level, lookupOrigin).thenComparingInt(pos -> Math.abs(level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY() - pos.getY()));

	/**
	 * Prioritizes closest positions on the xz plane that are higher in the sky.
	 */
	PortalLookup SKY = (level, lookupOrigin) -> AROUND_ORIGIN.provide(level, lookupOrigin).thenComparingInt(pos -> level.getMaxY() - pos.getY());

	/**
	 * Provides the comparator instance from the server level and the lookup origin.
	 * @param level the server level
	 * @param lookupOrigin the lookup origin
	 * @return the comparator
	 */
	Comparator<BlockPos> provide(ServerLevel level, BlockPos lookupOrigin);
}
