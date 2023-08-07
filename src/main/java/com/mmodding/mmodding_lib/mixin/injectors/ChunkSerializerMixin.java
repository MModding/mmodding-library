package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.unmapped.C_kedlqxpi;
import net.minecraft.util.Holder;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.chunk.palette.PalettedContainer;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {

    @Inject(method = "deserialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/poi/PointOfInterestStorage;initForPalette(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/chunk/ChunkSection;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void deserialize(ServerWorld world, PointOfInterestStorage poiStorage, ChunkPos pos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir, ChunkPos chunkPos, UpgradeData upgradeData, boolean bl, NbtList nbtList, int i, ChunkSection[] chunkSections, boolean bl2, ChunkManager chunkManager, LightingProvider lightingProvider, Registry<Biome> registry, Codec<C_kedlqxpi<Holder<Biome>>> codec, boolean bl3, int j, NbtCompound nbtCompound, int k, int l, PalettedContainer<BlockState> palettedContainer, C_kedlqxpi<Holder<Biome>> c_kedlqxpi, ChunkSection chunkSection) {
        if (!nbtCompound.contains("biomes", NbtElement.COMPOUND_TYPE)) {
            if (MModdingGlobalMaps.hasDefaultBiomes(world.getRegistryKey().getValue())) {
                RegistryKey<Biome> biomeKey = MModdingGlobalMaps.getDefaultBiomes(world.getRegistryKey().getValue()).apply(k);
                C_kedlqxpi<Holder<Biome>> readableContainer = new PalettedContainer<>(registry.asHolderIdMap(), registry.getHolderOrThrow(biomeKey), PalettedContainer.PaletteProvider.BIOME);
                chunkSections[l] = new ChunkSection(k, palettedContainer, readableContainer);
            }
        }
    }
}
