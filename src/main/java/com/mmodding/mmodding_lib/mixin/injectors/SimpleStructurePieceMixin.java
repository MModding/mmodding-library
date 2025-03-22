package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.StructureDuckInterface;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.piece.SimpleStructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(SimpleStructurePiece.class)
public abstract class SimpleStructurePieceMixin extends StructurePieceMixin {

	@Shadow
	protected abstract Identifier getId();

	@WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Lnet/minecraft/util/random/RandomGenerator;I)Z"))
	private boolean injectCollectors(Structure instance, ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, RandomGenerator random, int flags, Operation<Boolean> original) {
		((StructureDuckInterface) instance).mmodding_lib$provideCollectors(this.structureContainersCollector, this.structurePieceContainersCollector);
		return original.call(instance, world, pos, pivot, placementData, random, flags);
	}

	@Override
	public void mmodding_lib$provideCollectors(Consumer<BlockPos> structureContainersCollector, BiConsumer<Identifier, BlockPos> structurePieceContainersCollector) {
		this.structureContainersCollector = structureContainersCollector;
		this.structurePieceContainersCollector = structurePieceContainersCollector;
	}

	@Override
	protected boolean isSimple() {
		return true;
	}

	@Override
	protected Identifier getIdentifier() {
		return this.getId();
	}
}
