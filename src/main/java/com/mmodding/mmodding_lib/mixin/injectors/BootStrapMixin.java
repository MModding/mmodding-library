package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.base.MModdingBootStrapInitializer;
import net.minecraft.Bootstrap;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootStrapMixin {

	@Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;registerDefaultFlammables()V", shift = At.Shift.AFTER))
	private static void initialize(CallbackInfo ci) {

		for (var initializer: QuiltLoader.getEntrypointContainers(MModdingBootStrapInitializer.ENTRYPOINT_KEY, MModdingBootStrapInitializer.class)) {
			initializer.getEntrypoint().onInitializeBootStrap(initializer.getProvider());
		}
	}
}
