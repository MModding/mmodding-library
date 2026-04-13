package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.item.api.catalog.AdvancedBowItem;
import com.mmodding.library.java.api.object.Self;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin implements Self<ProjectileWeaponItem> {

	@ModifyExpressionValue(method = "createProjectile", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;ARROW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item onStoppedUsing(Item original) {
		return this.getObject() instanceof AdvancedBowItem advancedBowItem ? advancedBowItem.getDefaultArrowItem() : original;
	}
}
