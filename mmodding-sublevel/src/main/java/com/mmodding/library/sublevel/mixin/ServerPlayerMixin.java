package com.mmodding.library.sublevel.mixin;

import com.mmodding.library.sublevel.impl.ServerSublevel;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

	@Unique
	private String subLevelInfo = null;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void storeIfSublevel(MinecraftServer server, ServerLevel level, GameProfile gameProfile, ClientInformation clientInformation, CallbackInfo ci) {
		if (level instanceof ServerSublevel<?> sub) {
			this.subLevelInfo = sub.getMappedAttachment();
		}
	}

	@Inject(method = "setServerLevel", at = @At(value = "TAIL"))
	private void storeIfSublevel(ServerLevel level, CallbackInfo ci) {
		this.subLevelInfo = level instanceof ServerSublevel<?> sub ? sub.getMappedAttachment() : null;
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	private void saveSubLevelInfo(ValueOutput output, CallbackInfo ci) {
		if (this.subLevelInfo != null) {
			output.putString("mmodding:sublevel", this.subLevelInfo);
		}
	}
}
