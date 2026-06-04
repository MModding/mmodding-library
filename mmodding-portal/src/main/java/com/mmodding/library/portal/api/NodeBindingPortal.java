package com.mmodding.library.portal.api;

import com.mmodding.library.math.api.PosAndRot;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

import java.util.Comparator;

/**
 * A {@link NodeBindingPortal} is an extension of {@link Portal}s in cases
 * where a portal works by binding itself to a destination portal.
 */
public interface NodeBindingPortal extends Portal {

	/**
	 * The {@link PoiType} which allows to handle the portal nodes.
	 * <br>The blocks that are being supported for this point of interest indicates which
	 * blocks you will have to handle at found suitable positions.
	 * @return the point of interest resource key
	 */
	@ApiStatus.OverrideOnly
	ResourceKey<PoiType> pointOfInterest();

	/**
	 * The comparator defining which portal position should take priority if multiple
	 * are found. The minimal value is the one to take priority.
	 *
	 * <br><br>In example, the Nether Portal does:
	 * <br>
	 * <code>Comparator.comparingDouble<\u0000BlockPos>(p -> p.distSqr(lookupOrigin)).thenComparingInt(Vec3i::getY)</code>
	 * which priorities positions near the origin, and then prioritizes the lower ones
	 *
	 * <br><br>In example, if you want instead to select the higher positions first, you could do:
	 * <br>
	 * <code>Comparator.comparingDouble<\u0000BlockPos>(p -> p.distSqr(lookupOrigin)).thenComparingInt(p -> level.getMaxY() - p.getY())</code>
	 * @return the comparator
	 */
	@ApiStatus.OverrideOnly
	Comparator<BlockPos> closestSuitableComparator(ServerLevel level, BlockPos lookupOrigin);

	/**
	 * Evaluates the destination position from the found suitable block position.
	 * @param level the server level
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param suitablePos the suitable destination position
	 * @return the evaluated destination position to teleport to
	 */
	@ApiStatus.OverrideOnly
	default PosAndRot evaluateDestinationPosition(LevelReader level, Entity entity, BlockPos sourcePortalPos, BlockPos suitablePos) {
		return new PosAndRot(Vec3.atBottomCenterOf(suitablePos), 0, 0);
	}

	/**
	 * The {@link NodeBindingPortal}'s persistence states that once another portal
	 * is selected as the destination node, both nodes are storing each other
	 * position information in order to ensure the link between them.
	 * <br><br>In the case of two bound portals with persistence, the implementation of
	 * {@link #getPortalDestination(ServerLevel, Entity, BlockPos)} is not getting
	 * called anymore. {@link #evaluateDestinationPosition(LevelReader, Entity, BlockPos, BlockPos)} still is.
	 * <br><br>The persistence concept does not exist in vanilla: taking a nether portal
	 * in the end takes you to the nether, and going back in will put you in the overworld.
	 * @return the persistence
	 */
	@ApiStatus.OverrideOnly
	default boolean persistent() {
		return true;
	}
}
