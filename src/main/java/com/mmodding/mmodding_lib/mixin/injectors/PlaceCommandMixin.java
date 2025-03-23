package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.StructureStartDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
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

	@ModifyExpressionValue(method = "executePlaceStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/StructureFeature;generate(Lnet/minecraft/util/registry/DynamicRegistryManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/gen/RandomState;Lnet/minecraft/structure/StructureTemplateManager;JLnet/minecraft/util/math/ChunkPos;ILnet/minecraft/world/HeightLimitView;Ljava/util/function/Predicate;)Lnet/minecraft/structure/StructureStart;"))
	private static StructureStart injectProvider(StructureStart original, ServerCommandSource source, Holder<StructureFeature> structure, BlockPos pos) {
		RegistryKey<StructureFeature> structureKey = structure.getKey().orElseThrow();
		if (MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			((StructureStartDuckInterface) (Object) original).mmodding_lib$injectProvider(MModdingGlobalMaps.getSpreadLootInStructure(structureKey).createProvider());
		}
		return original;
	}

	@Inject(method = "executePlaceStructure", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
	private static void executeProvider(ServerCommandSource source, Holder<StructureFeature> structure, BlockPos pos, CallbackInfoReturnable<Integer> cir, @Local StructureStart structureStart) {
		((StructureStartDuckInterface) (Object) structureStart).mmodding_lib$spreadLoots(source.getWorld(), source.getWorld().getRandom());
	}
}
