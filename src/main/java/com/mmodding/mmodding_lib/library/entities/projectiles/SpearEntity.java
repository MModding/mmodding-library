package com.mmodding.mmodding_lib.library.entities.projectiles;

import com.mmodding.mmodding_lib.mixin.accessors.TridentEntityAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpearEntity extends TridentEntity {

	public SpearEntity(EntityType<? extends SpearEntity> entityType, World world, ItemStack stack) {
		super(entityType, world);
		((TridentEntityAccessor) this).setTridentStack(stack.copy());
	}

	public SpearEntity(EntityType<? extends SpearEntity> entityType, World world, LivingEntity owner, ItemStack stack) {
		this(entityType, world, stack);
		this.setPosition(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}
		this.dataTracker.set(TridentEntityAccessor.getLoyalty(), (byte) EnchantmentHelper.getLoyalty(stack));
		this.dataTracker.set(TridentEntityAccessor.getEnchanted(), stack.hasGlint());
	}
}
