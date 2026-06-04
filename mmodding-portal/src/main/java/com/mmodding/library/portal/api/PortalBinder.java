package com.mmodding.library.portal.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import com.mmodding.library.math.api.PosAndRot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

/**
 * A {@link PortalBinder} allows binding other portal positions to the current one.
 * <br>It is tied to a {@link ServerLevel}, and can be retrieved from it.
 */
@ApiStatus.NonExtendable
public interface PortalBinder {

	/**
	 * Looks up for a portal node around a specified position in a given radius,
	 * and binds it before giving back its position.
	 * @param portal the node binding portal
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if found
	 */
	default Optional<PosAndRot> lookupClosestForPosAndRot(NodeBindingPortal portal, Entity entity, BlockPos sourcePortalPos, BlockPos lookupOrigin, int radius) {
		throw new IllegalStateException();
	}

	/**
	 * Looks up for a portal node around a specified position in a given radius,
	 * and binds it before giving back its teleport transition.
	 * @param portal the node binding portal
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if found
	 */
	default Optional<TeleportTransition> lookupClosestForTransition(NodeBindingPortal portal, Entity entity, BlockPos sourcePortalPos, BlockPos lookupOrigin, int radius) {
		throw new IllegalStateException();
	}

	/**
	 * Determines suitable places to build a portal node, from a position, for a radius,
	 * and by offsetting the height until the given offset, ands binds the node.
	 * <br>It then sorts those positions by the closest suitable comparator,
	 * and tests them in order until one is accurate.
	 * <br>When found, it will bind the node and return its position.
	 * @param portal the node building portal
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param context the building context
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if built
	 * @param <C> the building context type
	 */
	default <C extends Record> Optional<PosAndRot> buildClosestForPosAndRot(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		throw new IllegalStateException();
	}

	/**
	 * Determines suitable places to build a portal node, from a position, for a radius,
	 * and by offsetting the height until the given offset.
	 * <br>It then sorts those positions by the closest suitable comparator,
	 * and tests them in order until one is accurate.
	 * <br>When found, it will bind the node and return its teleport transition.
	 * @param context the building context
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if built
	 * @param <C> the building context type
	 */
	default <C extends Record> Optional<TeleportTransition> buildClosestForTransition(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		throw new IllegalStateException();
	}

	/**
	 * Looks up first for a portal node, if found then binds it and returns the position of the entity to teleport to
	 * and the computed rotation for the entity.
	 * <br>If no node is found, then it builds one, before binding it and returning that position and rotation.
	 * @param context the building context
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if built
	 * @param <C> the building context type
	 */
	default <C extends Record> Optional<PosAndRot> lookupOrBuildClosestForPosAndRot(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		return this.lookupClosestForPosAndRot(portal, entity, sourcePortalPos, lookupOrigin, radius)
			.or(() -> this.buildClosestForPosAndRot(portal, entity, sourcePortalPos, context, lookupOrigin, radius));
	}

	/**
	 * Looks up first for a portal node, if found then binds it and returns the teleport transition for the entity to
	 * travel with.
	 * <br>If no node is found, then it builds one, before binding it and returning that teleport transition.
	 * @param context the building context
	 * @param entity the traveling entity
	 * @param sourcePortalPos the source portal position the entity traveled from
	 * @param lookupOrigin the lookup origin
	 * @param radius the radius
	 * @return the bound teleport position, if built
	 * @param <C> the building context type
	 */
	default <C extends Record> Optional<TeleportTransition> lookupOrBuildClosestForTransition(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		return this.lookupClosestForTransition(portal, entity, sourcePortalPos, lookupOrigin, radius)
			.or(() -> this.buildClosestForTransition(portal, entity, sourcePortalPos, context, lookupOrigin, radius));
	}

	@InjectedContent(ServerLevel.class)
	interface Container {

		/**
		 * Gets the portal binder for the current {@link ServerLevel} instance.
		 * @return the portal binder
		 */
		default PortalBinder getPortalBinder() {
			throw new IllegalStateException();
		}
	}
}
