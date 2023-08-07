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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Chunk.class)
public class ChunkMixin {

    @Inject(method = "fillSectionArray", at = @At(value = "NEW", target = "(ILnet/minecraft/util/registry/Registry;)Lnet/minecraft/world/chunk/ChunkSection;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void fillSectionArray(HeightLimitView view, Registry<Biome> biomeRegistry, ChunkSection[] sectionArray, CallbackInfo ci, int i) {
        if (view instanceof World world) {
            ChunkSectionDuckInterface ducked = ((ChunkSectionDuckInterface) sectionArray[i]);
            ducked.mmodding_lib$setWorld(world);
            ducked.mmodding_lib$reloadBiomeContainer();
        }
    }
}
