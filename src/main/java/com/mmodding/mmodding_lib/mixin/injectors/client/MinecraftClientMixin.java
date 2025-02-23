package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mmodding.mmodding_lib.library.sounds.client.music.MusicTypeSelectionCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@ModifyReturnValue(method = "getMusicType", at = @At("RETURN"))
	private MusicSound changeMusicType(MusicSound original) {
		return MusicTypeSelectionCallback.EVENT.invoker().select((MinecraftClient) (Object) this, original);
	}
}
