package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.PortalForcerDuckInterface;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends EntityMixin {

	@Inject(method = "getPortalRect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/PortalForcer;createPortal(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction$Axis;)Ljava/util/Optional;", shift = At.Shift.BEFORE))
	private void createPortal(ServerWorld destWorld, BlockPos destPos, boolean destIsNether, WorldBorder worldBorder, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
		PortalForcerDuckInterface duck = (PortalForcerDuckInterface) destWorld.getPortalForcer();
		duck.setUseCustomPortalElements(this.useCustomPortalElements);
		duck.setCustomPortalElements(this.customPortalElements.getFirst(), this.customPortalElements.getSecond());
	}

	@Inject(method = "getPortalRect", at = @At("TAIL"))
	private void getPortalRect(ServerWorld destWorld, BlockPos destPos, boolean destIsNether, WorldBorder worldBorder, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
		this.useCustomPortalElements = false;
	}
}
