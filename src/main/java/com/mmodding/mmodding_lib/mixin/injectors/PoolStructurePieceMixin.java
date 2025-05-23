package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.StructurePoolElementDuckInterface;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.piece.PoolStructurePiece;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(PoolStructurePiece.class)
public class PoolStructurePieceMixin extends StructurePieceMixin {

	@WrapOperation(method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/structure/StructureManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/random/RandomGenerator;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/BlockPos;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/pool/StructurePoolElement;generate(Lnet/minecraft/structure/StructureTemplateManager;Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/structure/StructureManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/random/RandomGenerator;Z)Z"))
	private boolean injectCollectors(StructurePoolElement instance, StructureTemplateManager structureTemplateManager, StructureWorldAccess structureWorldAccess, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos0, BlockPos blockPos1, BlockRotation blockRotation, BlockBox blockBox, RandomGenerator randomGenerator, boolean b, Operation<Boolean> original) {
		((StructurePoolElementDuckInterface) instance).mmodding_lib$provideCollectors(this.structureContainersCollector, this.structurePieceContainersCollector);
		return original.call(instance, structureTemplateManager, structureWorldAccess, structureManager, chunkGenerator, blockPos0, blockPos1, blockRotation, blockBox, randomGenerator, b);
	}

	@Override
	public void mmodding_lib$provideCollectors(Consumer<BlockPos> structureContainersCollector, BiConsumer<Identifier, BlockPos> structurePieceContainersCollector) {
		this.structureContainersCollector = structureContainersCollector;
		this.structurePieceContainersCollector = structurePieceContainersCollector;
	}

	@Override
	protected boolean isSimple() {
		return false;
	}

	@Override
	protected Identifier getIdentifier() {
		return null;
	}
}
