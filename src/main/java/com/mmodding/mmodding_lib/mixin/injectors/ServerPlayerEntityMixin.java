package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.PortalForcerDuckInterface;
import com.mmodding.mmodding_lib.ducks.ServerPlayerDuckInterface;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends EntityMixin implements ServerPlayerDuckInterface {

	@Shadow
	public abstract ServerWorld getWorld();

	@Unique
	public Optional<BlockLocating.Rectangle> getCustomPortalRect(ServerWorld destWorld, BlockPos destPos, boolean destIsNether, WorldBorder worldBorder) {
		// TODO : Make Automatically Register a POI for Each Portal in order to research them
		Optional<BlockLocating.Rectangle> optional = super.getPortalRect(destWorld, destPos, destIsNether, worldBorder);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis = this.getWorld().getBlockState(this.lastCustomPortalPosition).getOrEmpty(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
			PortalForcer forcer = destWorld.getPortalForcer();
			PortalForcerDuckInterface duckedForcer = (PortalForcerDuckInterface) forcer;

			duckedForcer.setUseCustomPortalElements(this.useCustomPortalElements);
			duckedForcer.setCustomPortalElements(this.customPortalElements.getFirst(), this.customPortalElements.getSecond());

			Optional<BlockLocating.Rectangle> optionalPortal = forcer.createPortal(destPos, axis);

			this.useCustomPortalElements = false;
			return optionalPortal;
		}
	}
}
