package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.StructureStartDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {

	@Unique
	private final Set<StructureStart> structureStarts = new HashSet<>(); // no duplicates; see Set implementation

	@ModifyExpressionValue(method = "method_41044", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/StructureFeature;generate(Lnet/minecraft/util/registry/DynamicRegistryManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/gen/RandomState;Lnet/minecraft/structure/StructureTemplateManager;JLnet/minecraft/util/math/ChunkPos;ILnet/minecraft/world/HeightLimitView;Ljava/util/function/Predicate;)Lnet/minecraft/structure/StructureStart;"))
	private StructureStart injectProvider(StructureStart original, StructureSet.StructureSelectionEntry structureSelectionEntry) {
		RegistryKey<StructureFeature> structureKey = structureSelectionEntry.structure().getKey().orElseThrow();
		if (MModdingGlobalMaps.hasSpreadLootInStructure(structureKey)) {
			((StructureStartDuckInterface) (Object) original).mmodding_lib$injectProvider(MModdingGlobalMaps.getSpreadLootInStructure(structureKey).createProvider());
		}
		return original;
	}

	@WrapOperation(method = "generateFeatures", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"))
	private void collectProviders(List<StructureStart> instance, Consumer<? super StructureStart> consumer, Operation<Void> original, @Local(argsOnly = true) StructureWorldAccess world) {
		original.call(instance, consumer);
		this.structureStarts.addAll(instance);
	}

	@Inject(method = "generateFeatures", at = @At(value = "TAIL"))
	private void executeProviders(StructureWorldAccess world, Chunk chunk, StructureManager structureManager, CallbackInfo ci) {
		this.structureStarts.forEach(structureStart -> ((StructureStartDuckInterface) (Object) structureStart).mmodding_lib$spreadLoots(world, world.getRandom()));
		this.structureStarts.clear();
	}
}
