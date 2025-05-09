package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldListWidget.Entry.class)
public class WorldListWidgetEntryMixin {

	@WrapOperation(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelSummary$ConversionWarning;promptsBackup()Z"))
	private boolean preventBackupConditionally(LevelSummary.ConversionWarning instance, Operation<Boolean> original) {
		if (!MModdingLibClient.LIBRARY_CLIENT_CONFIG.getContent().getBoolean("showExperimentalSettingsScreen")) {
			return false;
		}
		else {
			return original.call(instance);
		}
	}
}
