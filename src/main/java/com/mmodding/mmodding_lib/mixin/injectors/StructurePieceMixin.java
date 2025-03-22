package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.StructurePieceDuckInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.structure.piece.StructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(StructurePiece.class)
public abstract class StructurePieceMixin implements StructurePieceDuckInterface {

	@Unique
	protected Consumer<BlockPos> structureContainersCollector = null;

	@Unique
	protected BiConsumer<Identifier, BlockPos> structurePieceContainersCollector = null;

	@Inject(method = "addBlock", at = @At("TAIL"))
	private void collectLootableBlockEntities(StructureWorldAccess world, BlockState block, int x, int y, int z, BlockBox box, CallbackInfo ci, @Local BlockPos pos) {
		if (this.structureContainersCollector != null && world.getBlockEntity(pos) instanceof LootableContainerBlockEntity) {
			this.structureContainersCollector.accept(pos);
			if (this.structurePieceContainersCollector != null && this.isSimple()) {
				this.structurePieceContainersCollector.accept(this.getIdentifier(), pos);
			}
		}
	}

	@Unique
	protected abstract boolean isSimple();

	@Unique
	protected abstract Identifier getIdentifier();
}
