package com.mmodding.library.portal.impl;

import com.mmodding.library.math.api.Colliders;
import com.mmodding.library.math.api.PosAndRot;
import com.mmodding.library.portal.api.NodeBindingPortal;
import com.mmodding.library.portal.api.NodeBuildingPortal;
import com.mmodding.library.portal.api.PortalBinder;
import com.mmodding.library.portal.impl.storage.PortalNodeStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.StreamSupport;

public class PortalBinderImpl implements PortalBinder {

	private final ServerLevel destinationLevel;

	public PortalBinderImpl(ServerLevel destinationLevel) {
		this.destinationLevel = destinationLevel;
	}

	@Override
	public Optional<PosAndRot> lookupClosestForPosAndRot(NodeBindingPortal portal, Entity entity, BlockPos sourcePortalPos, BlockPos lookupOrigin, int radius) {
		PoiManager poiManager = this.destinationLevel.getPoiManager();
		poiManager.ensureLoadedAndValid(this.destinationLevel, lookupOrigin, radius);
		return poiManager.getInSquare(type -> type.is(portal.pointOfInterest()), lookupOrigin, radius, PoiManager.Occupancy.ANY)
			.map(PoiRecord::getPos).filter(this.destinationLevel.getWorldBorder()::isWithinBounds) // there's no need to filter more here, modders know what blocks can be detected by their POI
			.min(portal.closestSuitableComparator(this.destinationLevel, lookupOrigin))
			.map(suitablePos -> this.destinationLevel.getServer().getDataStorage().computeIfAbsent(PortalNodeStorage.TYPE).maybeBindLookup((ServerLevel) entity.level(), sourcePortalPos, this.destinationLevel, suitablePos, portal.persistent()))
			.map(pos -> portal.evaluateDestinationPosition(this.destinationLevel, entity, sourcePortalPos, pos));
	}

	@Override
	public Optional<TeleportTransition> lookupClosestForTransition(NodeBindingPortal portal, Entity entity, BlockPos sourcePortalPos, BlockPos lookupOrigin, int radius) {
		return this.lookupClosestForPosAndRot(portal, entity, sourcePortalPos, lookupOrigin, radius)
			.map(posAndRot -> new TeleportTransition(
				this.destinationLevel, posAndRot.pos(), Vec3.ZERO, posAndRot.yRot(), posAndRot.xRot(),
				Relative.union(Relative.DELTA, Relative.ROTATION),
				TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET))
			);
	}

	private boolean isFrameUnsuitable(Colliders portalFrameColliders, BlockPos currentPos) {
		return portalFrameColliders.collisions().stream()
			.map(currentPos::offset)
			.anyMatch(pos -> {
				BlockState state = this.destinationLevel.getBlockState(pos);
				return !state.canBeReplaced() || !state.getFluidState().isEmpty();
			});
	}

	@Override
	public <C extends Record> Optional<PosAndRot> buildClosestForPosAndRot(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		Colliders portalFrameColliders = portal.createPortalFrameColliders(context);
		ToIntFunction<BlockPos> comesFirst = pos -> portal.isPlacementFitting(this.destinationLevel, lookupOrigin, context, portalFrameColliders, pos) ? -1 : 1;;
		return StreamSupport.stream(BlockPos.spiralAround(lookupOrigin, radius, Direction.EAST, Direction.SOUTH).spliterator(), false)
			.map(BlockPos::new)
			.<BlockPos>mapMulti((pos, collector) -> BlockPos.betweenClosedStream(pos.atY(this.destinationLevel.getMinY()), pos.atY(this.destinationLevel.getMaxY())).forEach(p -> collector.accept(new BlockPos(p))))
			.filter(pos -> pos.getY() + portalFrameColliders.getMinY() > this.destinationLevel.getMinY() && pos.getY() + portalFrameColliders.getMaxY() < this.destinationLevel.getMaxY())
			.sorted(Comparator.comparingInt(comesFirst).thenComparing(portal.closestSuitableComparator(this.destinationLevel, lookupOrigin))) // since it's a sequential stream, it checks in order, that's what we want
			.dropWhile(pos -> isFrameUnsuitable(portalFrameColliders, pos)) // cheap on sequential stream, just drops any non-suitable higher-priority position
			.findFirst()
			.map(placementOrigin -> {
				portal.createPortal(this.destinationLevel, placementOrigin, context);
				this.destinationLevel.getServer().getDataStorage().computeIfAbsent(PortalNodeStorage.TYPE).maybeBindBuilt((ServerLevel) entity.level(), sourcePortalPos, this.destinationLevel, placementOrigin, portalFrameColliders, portal.persistent());
				return portal.evaluateDestinationPosition(this.destinationLevel, entity, sourcePortalPos, placementOrigin);
			});
	}

	@Override
	public <C extends Record> Optional<TeleportTransition> buildClosestForTransition(NodeBuildingPortal<C> portal, Entity entity, BlockPos sourcePortalPos, C context, BlockPos lookupOrigin, int radius) {
		return this.buildClosestForPosAndRot(portal, entity, sourcePortalPos, context, lookupOrigin, radius)
			.map(posAndRot -> new TeleportTransition(
				this.destinationLevel, posAndRot.pos(), Vec3.ZERO, posAndRot.yRot(), posAndRot.xRot(),
				Relative.union(Relative.DELTA, Relative.ROTATION),
				TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET))
			);
	}
}
