package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.interface_injections.SoundtrackPlayerContainer;
import com.mmodding.mmodding_lib.library.items.CustomBowItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemDropped;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends EntityMixin implements SoundtrackPlayerContainer {

	@Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir, double d, ItemEntity itemEntity) {
		ItemDropped itemDropped = AdvancedItemSettings.ITEM_DROPPED.get(stack.getItem());
		if (itemDropped != null) itemDropped.apply(stack, this.getWorld(), (PlayerEntity) (Object) this, itemEntity);
	}

	@ModifyExpressionValue(method = "getArrowType", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;ARROW:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item getArrowType(Item original, ItemStack stack) {
		return stack.getItem() instanceof CustomBowItem customBowItem ? customBowItem.getDefaultArrowItem() : original;
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public SoundtrackPlayer getSoundtrackPlayer() {
		return null;
	}
}
