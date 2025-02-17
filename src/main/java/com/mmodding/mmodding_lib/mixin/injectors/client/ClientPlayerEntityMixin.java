package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.library.soundtracks.client.ClientSoundtrackPlayer;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import com.mmodding.mmodding_lib.mixin.injectors.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntityMixin {

	@Unique
	private final ClientSoundtrackPlayer soundtrackPlayer = new ClientSoundtrackPlayer((ClientPlayerEntity) (Object) this, MinecraftClient.getInstance().getSoundManager());

	@Shadow
	@Final
	protected MinecraftClient client;

	@Shadow
	@Final
	private List<ClientPlayerTickable> tickables;

	@Shadow
	public float lastNauseaStrength;

	@Shadow
	public float nextNauseaStrength;

	@Shadow
	public abstract void closeHandledScreen();

	@Inject(method = "updateNausea", at = @At(value = "HEAD", shift = At.Shift.BY, by = 2), cancellable = true)
	private void updateNausea(CallbackInfo ci) {
		if (this.inCustomPortal) {
			this.lastNauseaStrength = this.nextNauseaStrength;

			if (this.client.currentScreen != null
				&& !this.client.currentScreen.isPauseScreen()
				&& !(this.client.currentScreen instanceof DeathScreen)
				&& !(this.client.currentScreen instanceof DownloadingTerrainScreen)) {
				if (this.client.currentScreen instanceof HandledScreen) {
					this.closeHandledScreen();
				}

				this.client.setScreen(null);
			}

			if (this.nextNauseaStrength == 0.0F) {
				this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
			}

			this.nextNauseaStrength += 0.0125F;
			if (this.nextNauseaStrength >= 1.0F) {
				this.nextNauseaStrength = 1.0F;
			}

			this.inCustomPortal = false;
			this.tickNetherPortalCooldown();

			ci.cancel();
		}
	}

	@Override
	public void mmodding_lib$setInCustomPortal(CustomSquaredPortal squaredPortal, World world, BlockPos pos) {
		super.mmodding_lib$setInCustomPortal(squaredPortal, world, pos);

		if (world instanceof ClientWorld clientWorld) {
			this.customPortalCache = squaredPortal.getPortalBlock();
			WorldUtils.repeatTaskUntil(clientWorld, 39, () -> this.customPortalCache = this.lastNauseaStrength > 0 ? squaredPortal.getPortalBlock() : null);
			WorldUtils.doTaskAfter(clientWorld, 40, () -> this.customPortalCache = null);
		}
	}

	@Override
	public SoundtrackPlayer getSoundtrackPlayer() {
		return this.soundtrackPlayer;
	}
}
