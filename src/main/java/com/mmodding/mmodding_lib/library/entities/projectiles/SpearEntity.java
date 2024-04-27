package com.mmodding.mmodding_lib.library.entities.projectiles;

import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.mixin.accessors.TridentEntityAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public class SpearEntity extends TridentEntity {

	private static final TrackedData<String> GLINT_PACK = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.STRING);

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
		this.dataTracker.set(SpearEntity.GLINT_PACK, GlintPack.getGlintPackId(stack).toString());
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SpearEntity.GLINT_PACK, null);
	}

	public Optional<Identifier> getGlintPack() {
		String identifier = this.dataTracker.get(SpearEntity.GLINT_PACK);
		if (identifier != null) {
			return Optional.ofNullable(Identifier.tryParse(identifier));
		}
		else {
			return Optional.empty();
		}
	}
}
