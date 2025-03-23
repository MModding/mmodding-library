package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.LootableContainerBlockEntityDuckInterface;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.random.RandomGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityMixin implements LootableContainerBlockEntityDuckInterface, Self<LootableContainerBlockEntity> {

	@Shadow
	public abstract void setStack(int slot, ItemStack stack);

	@Unique
	private Set<ItemStack> predeterminedLoots = null;

	@Inject(method = "deserializeLootTable", at = @At(value = "HEAD"))
	private void deserializePredeterminedLoots(NbtCompound nbt, CallbackInfoReturnable<Boolean> cir) {
		if (this.predeterminedLoots != null) {
			NbtList stacks = new NbtList();
			this.predeterminedLoots.forEach(stack -> stacks.add(stack.writeNbt(new NbtCompound())));
			nbt.put("predetermined_loots", stacks);
		}
	}

	@Inject(method = "serializeLootTable", at = @At(value = "HEAD"))
	private void serializePredeterminedLoots(NbtCompound nbt, CallbackInfoReturnable<Boolean> cir) {
		if (nbt.contains("predetermined_loots")) {
			if (this.predeterminedLoots == null) {
				this.predeterminedLoots = new HashSet<>();
			}
			NbtList stacks = nbt.getList("predetermined_loots", NbtElement.COMPOUND_TYPE);
			for (NbtElement element : stacks) {
				NbtCompound stack = (NbtCompound) element;
				this.predeterminedLoots.add(ItemStack.fromNbt(stack));
			}
		}
	}

	@Inject(method = "checkLootInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;supplyInventory(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/loot/context/LootContext;)V"))
	private void injectedPredeterminedLoot(PlayerEntity player, CallbackInfo ci) {
		if (this.predeterminedLoots != null) {
			this.predeterminedLoots.forEach(stack -> {
				int slot = this.getRandomEmptySlotIndex();
				if (slot != -1) {
					this.setStack(slot, stack);
				}
			});
		}
	}

	@Unique
	private int getRandomEmptySlotIndex() {
		List<Integer> emptySlotIndexes = this.emptySlotIndexes();
		return !emptySlotIndexes.isEmpty() ? emptySlotIndexes.get(RandomGenerator.createLegacy().nextInt(emptySlotIndexes.size())) : -1;
	}

	@Unique
	private List<Integer> emptySlotIndexes() {
		List<Integer> emptySlotIndexes = new ArrayList<>();
		for (int i = 0; i < this.getObject().size(); i++) {
			if (this.getObject().getStack(i).isEmpty()) {
				emptySlotIndexes.add(i);
			}
		}
		return emptySlotIndexes;
	}

	@Override
	public void mmodding_lib$addPredeterminedLoot(ItemStack predeterminedLoots) {
		if (this.predeterminedLoots == null) {
			this.predeterminedLoots = new HashSet<>(Set.of(predeterminedLoots));
		}
		else {
			this.predeterminedLoots.add(predeterminedLoots);
		}
	}

	@Override
	public void mmodding_lib$clearPredeterminedLoot() {
		this.predeterminedLoots = null;
	}
}
