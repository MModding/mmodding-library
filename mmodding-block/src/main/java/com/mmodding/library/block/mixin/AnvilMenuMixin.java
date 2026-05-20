package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.block.api.catalog.AdvancedAnvilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

	public AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
		super(menuType, containerId, inventory, access, itemInputSlots);
	}

	@ModifyConstant(method = "lambda$onTake$0", constant = @Constant(floatValue = 0.12f))
	private static float replaceDamagePercentage(float original, @Local(argsOnly = true, name = "level") Level level, @Local(argsOnly = true, name = "pos") BlockPos pos) {
		if (level.getBlockState(pos).getBlock() instanceof AdvancedAnvilBlock anvil) {
			return anvil.getDamagingChance();
		}
		else {
			return original;
		}
	}

	@WrapOperation(method = "lambda$onTake$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;levelEvent(ILnet/minecraft/core/BlockPos;I)V"))
	private static void modifySounds(Level instance, int type, BlockPos pos, int data, Operation<Void> original) {
		if (instance.getBlockState(pos).getBlock() instanceof AdvancedAnvilBlock anvil) {
			if (type == LevelEvent.SOUND_ANVIL_BROKEN) {
				instance.playSound(null, pos, anvil.getBrokenSound(), SoundSource.BLOCKS, 1.0f, instance.getRandom().nextFloat() * 0.1f + 0.9f); // not sure if using the level random is nice or not
			}
			else if (type == LevelEvent.SOUND_ANVIL_USED) {
				instance.playSound(null, pos, anvil.getForgeSound(), SoundSource.BLOCKS, 1.0f, instance.getRandom().nextFloat() * 0.1f + 0.9f);
			}
		}
		else {
			original.call(instance, type, pos, data);
		}
	}

	@ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
	private int modifyExperienceLimit(int original) {
		Block instance = this.access.evaluate((level, pos) -> level.getBlockState(pos).getBlock(), Blocks.AIR);
		if (instance instanceof AdvancedAnvilBlock anvil) {
			return anvil.getExperienceLimit();
		}
		else {
			return original;
		}
	}

	@ModifyConstant(method = "createResult", constant = @Constant(intValue = 39))
	private int modifyExperienceCap(int original) {
		Block instance = this.access.evaluate((level, pos) -> level.getBlockState(pos).getBlock(), Blocks.AIR);
		if (instance instanceof AdvancedAnvilBlock anvil) {
			return anvil.getExperienceLimit() - 1;
		}
		else {
			return original;
		}
	}
}
