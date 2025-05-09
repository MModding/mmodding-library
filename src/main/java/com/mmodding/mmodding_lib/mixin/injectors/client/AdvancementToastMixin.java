package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.client.advancements.AdvancementChallengeCompletionSoundCallback;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {

	@Shadow
	@Final
	private Advancement advancement;

	@WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/PositionedSoundInstance;master(Lnet/minecraft/sound/SoundEvent;FF)Lnet/minecraft/client/sound/PositionedSoundInstance;"))
	private PositionedSoundInstance wrapSoundEvent(SoundEvent sound, float pitch, float volume, Operation<PositionedSoundInstance> original) {
		return original.call(AdvancementChallengeCompletionSoundCallback.EVENT.invoker().select(MinecraftClient.getInstance(), this.advancement, sound), pitch, volume);
	}
}
