package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.ducks.StructureStartDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.worldgen.structures.StructureSpreadLoot;
import net.minecraft.server.command.PlaceComand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlaceComand.class)
public class PlaceCommandMixin {

	@Inject(method = "executePlaceStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Holder;value()Ljava/lang/Object;", shift = At.Shift.AFTER))
	private static void initializeProvider(ServerCommandSource source, Holder<StructureFeature> structure, BlockPos pos, CallbackInfoReturnable<Integer> cir, @Share("structure_spread_loot") LocalRef<StructureSpreadLoot.StructureSpreadLootProvider> structureSpreadLoot) {
		RegistryKey<StructureFeature> structureKey = structure.getKey().orElseThrow();
		if (MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			structureSpreadLoot.set(MModdingGlobalMaps.getSpreadLootInStructure(structureKey).createProvider());
		}
	}

	@ModifyExpressionValue(method = "executePlaceStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/StructureFeature;generate(Lnet/minecraft/util/registry/DynamicRegistryManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/gen/RandomState;Lnet/minecraft/structure/StructureTemplateManager;JLnet/minecraft/util/math/ChunkPos;ILnet/minecraft/world/HeightLimitView;Ljava/util/function/Predicate;)Lnet/minecraft/structure/StructureStart;"))
	private static StructureStart injectCollectors(StructureStart original, ServerCommandSource source, Holder<StructureFeature> structure, BlockPos pos, @Share("structure_spread_loot") LocalRef<StructureSpreadLoot.StructureSpreadLootProvider> structureSpreadLoot) {
		RegistryKey<StructureFeature> structureKey = structure.getKey().orElseThrow();
		if (MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			((StructureStartDuckInterface) (Object) original).mmodding_lib$provideCollectors(structureSpreadLoot.get().structureContainersCollector(), structureSpreadLoot.get().structurePieceContainersCollector());
		}
		return original;
	}

	@Inject(method = "executePlaceStructure", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
	private static void executeProvider(ServerCommandSource source, Holder<StructureFeature> structure, BlockPos pos, CallbackInfoReturnable<Integer> cir, @Share("structure_spread_loot") LocalRef<StructureSpreadLoot.StructureSpreadLootProvider> structureSpreadLoot) {
		RegistryKey<StructureFeature> structureKey = structure.getKey().orElseThrow();
		if (MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			structureSpreadLoot.get().spreadLoots(source.getWorld(), source.getWorld().getRandom());
		}
	}
}
