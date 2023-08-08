package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.ChunkSectionDuckInterface;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public class ChunkMixin {

    @Inject(method = "fillSectionArray", at = @At("TAIL"))
    private static void fillSectionArray(HeightLimitView view, Registry<Biome> biomeRegistry, ChunkSection[] sectionArray, CallbackInfo ci) {
		if (view instanceof World world) {
            for (ChunkSection section : sectionArray) {
                ChunkSectionDuckInterface ducked = ((ChunkSectionDuckInterface) section);
				ducked.mmodding_lib$setWorld(world);
				ducked.mmodding_lib$reloadBiomeContainer();
            }
		}
    }
}
