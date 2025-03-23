package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.StructureStartDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.piece.StructurePieceSerializationContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {

	@ModifyExpressionValue(method = "readStructureStarts", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StructureStart;loadStaticStart(Lnet/minecraft/structure/piece/StructurePieceSerializationContext;Lnet/minecraft/nbt/NbtCompound;J)Lnet/minecraft/structure/StructureStart;"))
	private static StructureStart injectProvider(StructureStart original, StructurePieceSerializationContext structurePieceSerializationContext, @Local Identifier identifier) {
		RegistryKey<StructureFeature> structureKey = RegistryKey.of(Registry.STRUCTURE_WORLDGEN, identifier);
		if (original != null && MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			((StructureStartDuckInterface) (Object) original).mmodding_lib$injectProvider(MModdingGlobalMaps.getSpreadLootInStructure(structureKey).createProvider());
		}
		return original;
	}
}
