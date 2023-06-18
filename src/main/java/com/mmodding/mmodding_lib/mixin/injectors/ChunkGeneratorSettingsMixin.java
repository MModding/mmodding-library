package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.worldgen.ChunkGeneratorRegistration;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {

	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void bootstrap(Registry<ChunkGeneratorSettings> registry, CallbackInfoReturnable<Holder<ChunkGeneratorSettings>> cir) {
		ChunkGeneratorRegistration.forEachGenerator(
			pair -> BuiltinRegistries.register(registry, pair.getLeft(), pair.getRight())
		);
	}
}
