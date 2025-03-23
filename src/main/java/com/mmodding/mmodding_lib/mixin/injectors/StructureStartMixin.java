package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.StructurePieceDuckInterface;
import com.mmodding.mmodding_lib.ducks.StructureStartDuckInterface;
import com.mmodding.mmodding_lib.library.worldgen.structures.StructureSpreadLoot;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.piece.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureStart.class)
public class StructureStartMixin implements StructureStartDuckInterface {

	@Unique
	private StructureSpreadLoot.StructureSpreadLootProvider provider = null;

	@WrapOperation(method = "placeInChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/piece/StructurePiece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/structure/StructureManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/random/RandomGenerator;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V"))
	private void injectCollectors(StructurePiece instance, StructureWorldAccess structureWorldAccess, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomGenerator randomGenerator, BlockBox blockBox, ChunkPos chunkPos, BlockPos blockPos, Operation<Void> original) {
		if (this.provider != null) {
			((StructurePieceDuckInterface) instance).mmodding_lib$provideCollectors(this.provider.structureContainersCollector(), this.provider.structurePieceContainersCollector());
		}
		original.call(instance, structureWorldAccess, structureManager, chunkGenerator, randomGenerator, blockBox, chunkPos, blockPos);
	}

	@Override
	public void mmodding_lib$injectProvider(StructureSpreadLoot.StructureSpreadLootProvider provider) {
		this.provider = provider;
	}

	@Override
	public void mmodding_lib$spreadLoots(ServerWorldAccess world, RandomGenerator random) {
		if (this.provider != null) {
			this.provider.erasePreviousLoots(world);
			this.provider.spreadLoots(world, random);
		}
	}
}
