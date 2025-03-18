package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.ducks.PlayerEntityDuckInterface;
import com.mmodding.mmodding_lib.interface_injections.PlayerStructureLookup;
import com.mmodding.mmodding_lib.interface_injections.SoundtrackPlayerContainer;
import com.mmodding.mmodding_lib.library.entities.data.MModdingTrackedDataHandlers;
import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import com.mmodding.mmodding_lib.library.items.CustomBowItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemDropped;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.StructureFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends EntityMixin implements PlayerEntityDuckInterface, PlayerStructureLookup, SoundtrackPlayerContainer {

	@Unique
	private final SyncableData<List<Identifier>> closestStructures = new SyncableData<>(
		List.of(),
		(PlayerEntity) (Object) this,
		new MModdingIdentifier("closest_structures"),
		MModdingTrackedDataHandlers.IDENTIFIER_LIST
	);

	@Unique
	private int structureUpdateCooldown = 0;

	@Unique
	private boolean invincible;

	@Inject(method = "tick", at = @At("TAIL"))
	private void updateCurrentStructure(CallbackInfo ci) {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			this.structureUpdateCooldown++;
			if (this.structureUpdateCooldown >= 100) {
				this.structureUpdateCooldown = 0;
				Set<StructureFeature> structureFeature = serverWorld.getStructureManager().getStructuresAt(this.getBlockPos()).keySet();
				Registry<StructureFeature> structureFeatureRegistry = serverWorld.getRegistryManager().get(Registry.STRUCTURE_WORLDGEN);
				this.closestStructures.set(structureFeature.stream().map(structureFeatureRegistry::getId).toList());
				this.closestStructures.synchronize();
			}
		}
	}

	@Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir, double d, ItemEntity itemEntity) {
		ItemDropped itemDropped = AdvancedItemSettings.ITEM_DROPPED.get(stack.getItem());
		if (itemDropped != null) itemDropped.apply(stack, this.getWorld(), (PlayerEntity) (Object) this, itemEntity);
	}

	@ModifyExpressionValue(method = "getArrowType", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;ARROW:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item getArrowType(Item original, ItemStack stack) {
		return stack.getItem() instanceof CustomBowItem customBowItem ? customBowItem.getDefaultArrowItem() : original;
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readInvincibility(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains("MModdingInvincibility")) {
			this.invincible = true;
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeInvincibility(NbtCompound nbt, CallbackInfo ci) {
		if (this.invincible) {
			nbt.putBoolean("MModdingInvincibility", true);
		}
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void computeInvincibility(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.mmodding_lib$isInvincible()) {
			cir.setReturnValue(false);
		}
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public Set<RegistryKey<StructureFeature>> getClosestStructures() {
		return this.closestStructures.get()
			.stream()
			.map(identifier -> RegistryKey.of(Registry.STRUCTURE_WORLDGEN, identifier))
			.collect(Collectors.toSet());
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public SoundtrackPlayer getSoundtrackPlayer() {
		return null;
	}

	@Override
	public boolean mmodding_lib$isInvincible() {
		return this.invincible;
	}

	@Override
	public boolean mmodding_lib$setInvincible(boolean invincible) {
		return this.invincible = invincible;
	}
}
