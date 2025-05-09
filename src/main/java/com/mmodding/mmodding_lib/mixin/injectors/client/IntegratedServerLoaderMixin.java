package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {

	@WrapMethod(method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V")
	private void preventBackupConditionally(Screen parentScreen, String worldName, boolean safeMode, boolean requireBackup, Operation<Void> original) {
		original.call(parentScreen, worldName, safeMode, MModdingLibClient.LIBRARY_CLIENT_CONFIG.getContent().getBoolean("showExperimentalSettingsScreen") && requireBackup);
	}
}
