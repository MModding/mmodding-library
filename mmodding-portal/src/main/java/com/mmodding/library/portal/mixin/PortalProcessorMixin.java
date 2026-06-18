package com.mmodding.library.portal.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.math.api.PosAndRot;
import com.mmodding.library.portal.api.NodeBindingPortal;
import com.mmodding.library.portal.impl.storage.PortalNodeStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PortalProcessor;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(PortalProcessor.class)
public class PortalProcessorMixin {

	@Shadow
	@Final
	public Portal portal;

	@Shadow
	private BlockPos entryPosition;

	@WrapMethod(method = "getPortalDestination")
	private TeleportTransition applyNodePersistence(ServerLevel serverLevel, Entity entity, Operation<TeleportTransition> original) {
		PortalNodeStorage storage = serverLevel.getServer().getDataStorage().get(PortalNodeStorage.TYPE);
		if (storage != null && this.portal instanceof NodeBindingPortal nodeBased && nodeBased.persistent()) {
			GlobalPos maybePos = storage.getPossibleNodeTarget(serverLevel, this.entryPosition);
			if (maybePos != null) {
				ServerLevel destinationLevel = Objects.requireNonNull(serverLevel.getServer().getLevel(maybePos.dimension()), "Invalid level: " + maybePos.dimension());
				BlockPos suitablePos = maybePos.pos();
				PosAndRot posAndRot = nodeBased.evaluateDestinationPosition(destinationLevel, entity, this.entryPosition, suitablePos);
				return new TeleportTransition(
					destinationLevel, posAndRot.pos(), Vec3.ZERO, posAndRot.yRot(), posAndRot.xRot(),
					Relative.union(Relative.DELTA, Relative.ROTATION),
					TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
				);
			}
		}
		return original.call(serverLevel, entity);
	}
}
