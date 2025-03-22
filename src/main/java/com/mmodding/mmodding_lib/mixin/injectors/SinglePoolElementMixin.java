package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.ducks.StructureDuckInterface;
import net.minecraft.structure.Structure;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(SinglePoolElement.class)
public class SinglePoolElementMixin extends StructurePoolElementMixin {

	@ModifyExpressionValue(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/pool/SinglePoolElement;getStructure(Lnet/minecraft/structure/StructureTemplateManager;)Lnet/minecraft/structure/Structure;"))
	private Structure injectCollectors(Structure original) {
		((StructureDuckInterface) original).mmodding_lib$provideCollectors(this.structureContainersCollector, this.structurePieceContainersCollector);
		return original;
	}

	@Override
	public void mmodding_lib$provideCollectors(Consumer<BlockPos> structureContainersCollector, BiConsumer<Identifier, BlockPos> structurePieceContainersCollector) {
		this.structureContainersCollector = structureContainersCollector;
		this.structurePieceContainersCollector = structurePieceContainersCollector;
	}
}
