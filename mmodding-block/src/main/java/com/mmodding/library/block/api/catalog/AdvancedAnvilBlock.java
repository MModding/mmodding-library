package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class AdvancedAnvilBlock extends AnvilBlock {

	private final float damagingChance;
	private final int experienceLimit;
	private final float damagePerDistance;
	private final int maxDamage;
	private final Supplier<? extends AdvancedAnvilBlock> next;
	private final SoundEvent landingSound;
	private final SoundEvent forgeSound;
	private final SoundEvent brokenSound;

	public AdvancedAnvilBlock(float damagingChance, int experienceLimit, float damagePerDistance, int maxDamage, @Nullable Supplier<? extends AdvancedAnvilBlock> next, Properties properties) {
		this(damagingChance, experienceLimit, damagePerDistance, maxDamage, next, SoundEvents.ANVIL_LAND, SoundEvents.ANVIL_USE, SoundEvents.ANVIL_DESTROY, properties);
	}

	public AdvancedAnvilBlock(float damagingChance, int experienceLimit, float damagePerDistance, int maxDamage, @Nullable Supplier<? extends AdvancedAnvilBlock> next, SoundEvent landingSound, SoundEvent forgeSound, SoundEvent brokenSound, Properties properties) {
		super(properties);
		this.damagingChance = damagingChance;
		this.experienceLimit = experienceLimit;
		this.damagePerDistance = damagePerDistance;
		this.maxDamage = maxDamage;
		this.next = next;
		this.landingSound = landingSound;
		this.forgeSound = forgeSound;
		this.brokenSound = brokenSound;
	}

	@Override
	protected void falling(final FallingBlockEntity entity) {
		entity.setHurtsEntities(this.damagePerDistance, this.maxDamage);
	}

	@Override
	public void onLand(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity) {
		if (!entity.isSilent()) {
			level.playSound(null, pos, this.getLandingSound(), SoundSource.BLOCKS, 1.0f, entity.getRandom().nextFloat() * 0.1f + 0.9f);
		}
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
		if (!entity.isSilent()) {
			level.playSound(null, pos, this.getBrokenSound(), SoundSource.BLOCKS, 1.0f, entity.getRandom().nextFloat() * 0.1f + 0.9f);
		}
	}

	@Override
	public DamageSource getFallDamageSource(Entity entity) {
		return super.getFallDamageSource(entity);
	}

	public float getDamagingChance() {
		return this.damagingChance;
	}

	public int getExperienceLimit() {
		return this.experienceLimit;
	}

	@Nullable
	public AdvancedAnvilBlock getNextAnvil() {
		return this.next != null ? this.next.get() : null;
	}

	public SoundEvent getLandingSound() {
		return this.landingSound;
	}

	public SoundEvent getForgeSound() {
		return this.forgeSound;
	}

	public SoundEvent getBrokenSound() {
		return this.brokenSound;
	}
}
