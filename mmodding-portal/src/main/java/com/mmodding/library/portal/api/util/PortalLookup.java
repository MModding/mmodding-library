package com.mmodding.library.portal.api.util;

import com.mmodding.library.portal.impl.util.PortalLookupImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import java.util.Comparator;

@FunctionalInterface
public interface PortalLookup {

	/**
	 * Prioritizes closest positions on the xz plane that are deeper.
	 */
	PortalLookup DEPTH = (level, lookupOrigin) -> anchoring((int) (level.getMinY() + 0.125f * level.getHeight())).provide(level, lookupOrigin);

	/**
	 * Prioritizes closest positions on the xz plane that are near the surface.
	 */
	PortalLookup SURFACE = (level, lookupOrigin) -> anchoring(PortalLookupImpl.getMotionBlockingForceLoad(level, lookupOrigin)).provide(level, lookupOrigin);

	/**
	 * Prioritizes closest positions on the xz plane that are higher in the sky.
	 */
	PortalLookup SKY = (level, lookupOrigin) -> anchoring((int) (level.getMinY() + 0.75f * level.getHeight())).provide(level, lookupOrigin);

	static PortalLookup anchoring(int y) {
		return (_, lookupOrigin) -> Comparator.comparingDouble(pos -> pos.distSqr(lookupOrigin.atY(y)));
	}

	/**
	 * Provides the comparator instance from the server level and the lookup origin.
	 * @param level the level
	 * @param lookupOrigin the lookup origin
	 * @return the comparator
	 */
	Comparator<BlockPos> provide(LevelReader level, BlockPos lookupOrigin);
}
