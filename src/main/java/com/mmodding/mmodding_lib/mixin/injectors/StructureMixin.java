package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.ducks.LootableContainerBlockEntityDuckInterface;
import com.mmodding.mmodding_lib.ducks.StructureDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(Structure.class)
public class StructureMixin implements StructureDuckInterface {

	@Unique
	private Identifier identifier = new Identifier("minecraft", "blank");

	@Inject(method = "place", at = @At("HEAD"))
	private void initiateContainerList(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, RandomGenerator random, int flags, CallbackInfoReturnable<Boolean> cir, @Share("containers") LocalRef<List<BlockPos>> containers) {
		containers.set(new ArrayList<>());
	}

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putLong(Ljava/lang/String;J)V"))
	private void injectContainerPosition(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, RandomGenerator random, int flags, CallbackInfoReturnable<Boolean> cir, @Local Structure.StructureBlockInfo structureBlockInfo, @Share("containers") LocalRef<List<BlockPos>> containers) {
		List<BlockPos> array = containers.get();
		array.add(structureBlockInfo.pos);
		containers.set(array);
	}

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 3))
	private void spreadPredeterminedLoots(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, RandomGenerator random, int flags, CallbackInfoReturnable<Boolean> cir, @Share("containers") LocalRef<List<BlockPos>> containers) {
		if (MModdingGlobalMaps.hasLocalCommonLootOfStructurePiece(this.identifier) && !containers.get().isEmpty()) {
			List<ItemStack> localCommonLoot = MModdingGlobalMaps.getLocalCommonLootOfStructurePiece(this.identifier);
			for (ItemStack stack : localCommonLoot) {
				BlockPos containerPos = containers.get().get(random.nextInt(containers.get().size()));
				if (world.getBlockEntity(containerPos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
					((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$addPredeterminedLoot(stack);
				}
			}
		}
	}

	@Override
	public void mmodding_lib$setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
}
