package com.mmodding.library.portal.api;

import com.mmodding.library.math.api.Colliders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.ApiStatus;

/**
 * A {@link NodeBuildingPortal} is an extension of {@link NodeBindingPortal}s in cases where
 * your portal will generate a new destination portal if it does not find any to bind to.
 * @param <C> a record class which acts as the required context to build the portal
 */
public interface NodeBuildingPortal<C extends Record> extends NodeBindingPortal {

	/**
	 * The colliders of the portal frame. It is used to determine
	 * if the portal frame is suitable for placement at a given position.
	 * @return the box which represents the required space
	 * @apiNote The origin vector of your collider will be used to compute the placement origin; keep that in made
	 * when implementing {@link #evaluateDestinationPosition(LevelReader, Entity, BlockPos, BlockPos)}, as you will
	 * very likely want the behavior of this method on the suitable position (determined from the point of interest
	 * lookup position results) to be the same for the placement origin.
	 * <br>In example, if your point of interest targets the portal block directly, you should consider setting the origin
	 * of your collider as a position where a portal block would be.
	 */
	@ApiStatus.OverrideOnly
	Colliders createPortalFrameColliders(C context);

	/**
	 * Checks if a candidate for the portal placement origin is valid.
	 * <br>It does not filter other positions out, it only will make
	 * them considered after every other fitting placement choices.
	 * @param level the level
	 * @param lookupOrigin the lookup origin
	 * @param context the context
	 * @param colliders the colliders
	 * @param placementCandidateOrigin the placement candidate origin block position
	 * @apiNote
	 */
	@ApiStatus.OverrideOnly
	default boolean isPlacementFitting(LevelReader level, BlockPos lookupOrigin, C context, Colliders colliders, BlockPos placementCandidateOrigin) {
		return colliders.lowestCollisions()
			.stream()
			.map(placementCandidateOrigin::offset)
			.map(BlockPos::below)
			.allMatch(pos -> level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP));
	}

	/**
	 * The method defining the portal destination generation.
	 * @param level the destination level
	 * @param placementOrigin the origin of the suitable position
	 * @param context the generation context
	 */
	@ApiStatus.OverrideOnly
	void createPortal(ServerLevel level, BlockPos placementOrigin, C context);
}
