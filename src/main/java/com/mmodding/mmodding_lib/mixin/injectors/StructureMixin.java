package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.ducks.StructureDuckInterface;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(Structure.class)
public class StructureMixin implements StructureDuckInterface {

	@Unique
	private static final Identifier BLANK = new Identifier("blank");

	@Unique
	private Identifier identifier = StructureMixin.BLANK;

	@Unique
	private Consumer<BlockPos> structureContainersCollector = null;

	@Unique
	private BiConsumer<Identifier, BlockPos> structurePieceContainersCollector = null;

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putLong(Ljava/lang/String;J)V"))
	private void injectContainerPosition(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, RandomGenerator random, int flags, CallbackInfoReturnable<Boolean> cir, @Local Structure.StructureBlockInfo structureBlockInfo) {
		MModdingLib.LIBRARY_CONTAINER.getLogger().warn(this.identifier.toString());
		if (this.structureContainersCollector != null) {
			MModdingLib.LIBRARY_CONTAINER.getLogger().warn("uuu");
			this.structureContainersCollector.accept(structureBlockInfo.pos);
			if (this.structurePieceContainersCollector != null && !this.identifier.equals(StructureMixin.BLANK)) {
				this.structurePieceContainersCollector.accept(this.identifier, structureBlockInfo.pos);
			}
		}
	}

	@Override
	public void mmodding_lib$provideCollectors(Consumer<BlockPos> structureContainersCollector, BiConsumer<Identifier, BlockPos> structurePieceContainersCollector) {
		this.structureContainersCollector = structureContainersCollector;
		this.structurePieceContainersCollector = structurePieceContainersCollector;
	}

	@Override
	public void mmodding_lib$setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
}
