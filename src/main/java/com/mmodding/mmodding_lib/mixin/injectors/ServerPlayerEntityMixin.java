package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.PortalForcerDuckInterface;
import com.mmodding.mmodding_lib.ducks.ServerPlayerDuckInterface;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.library.soundtracks.server.ServerSoundtrackPlayer;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.damage.DamageSource;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntityMixin implements ServerPlayerDuckInterface {

	@Unique
	private final ServerSoundtrackPlayer soundtrackPlayer = new ServerSoundtrackPlayer((ServerPlayerEntity) (Object) this);

	@Shadow
	public abstract ServerWorld getWorld();

	@Inject(method = "onDeath", at = @At("TAIL"))
	private void resetSoundtrackPlayer(DamageSource source, CallbackInfo ci) {
		this.soundtrackPlayer.unlock();
		this.soundtrackPlayer.unseal();
		this.soundtrackPlayer.clear();
	}

	@Unique
	public Optional<BlockLocating.Rectangle> mmodding_lib$getCustomPortalRect(ServerWorld destWorld, BlockPos destPos, WorldBorder worldBorder) {
		PortalForcer forcer = destWorld.getPortalForcer();
		PortalForcerDuckInterface ducked = (PortalForcerDuckInterface) forcer;
		Optional<BlockLocating.Rectangle> optional = ducked.mmodding_lib$searchCustomPortal(this.customPortal.getPortalLink().getPoiKey(), destPos, worldBorder);

		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis = this.getWorld().getBlockState(this.lastCustomPortalPosition).getOrEmpty(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);

			ducked.mmodding_lib$setUseCustomPortalElements(this.useCustomPortalElements);
			ducked.mmodding_lib$setCustomPortal(this.customPortal);

			Optional<BlockLocating.Rectangle> optionalPortal = forcer.createPortal(destPos, axis);

			this.useCustomPortalElements = false;
			return optionalPortal;
		}
	}

	@Override
	public SoundtrackPlayer getSoundtrackPlayer() {
		return this.soundtrackPlayer;
	}
}
