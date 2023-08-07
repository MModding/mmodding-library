package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.ChunkSectionDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.unmapped.C_kedlqxpi;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.palette.PalettedContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin implements ChunkSectionDuckInterface {

    @Unique
    World world;

    @Unique
    Registry<Biome> biomeRegistry;

    @Shadow
    private C_kedlqxpi<Holder<Biome>> biomeContainer;

    @Shadow @Final private int yOffset;

    @Inject(method = "<init>(ILnet/minecraft/util/registry/Registry;)V", at = @At("TAIL"))
    private void init(int yOffset, Registry<Biome> biomeRegistry, CallbackInfo ci) {
        this.biomeRegistry = biomeRegistry;
    }

    @Override
    public World mmodding_lib$getWorld() {
        return this.world;
    }

    @Override
    public void mmodding_lib$setWorld(World world) {
        this.world = world;
    }

    @Override
    public void mmodding_lib$reloadBiomeContainer() {

        Identifier identifier = this.world.getRegistryKey().getValue();

        if (MModdingGlobalMaps.hasDefaultBiomes(identifier)) {
            RegistryKey<Biome> registryKey = MModdingGlobalMaps.getDefaultBiomes(identifier).apply(this.yOffset);
            this.biomeContainer = new PalettedContainer<>(
                this.biomeRegistry.asHolderIdMap(),
                this.biomeRegistry.getHolderOrThrow(registryKey), PalettedContainer.PaletteProvider.BIOME
            );
        }
    }
}
