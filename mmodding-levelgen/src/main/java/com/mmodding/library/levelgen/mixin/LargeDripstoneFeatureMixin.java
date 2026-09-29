package com.mmodding.library.levelgen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.levelgen.api.feature.catalog.AdvancedLargeDripstoneFeature;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(LargeDripstoneFeature.class)
public class LargeDripstoneFeatureMixin {

	@Shadow
	@Final
	private HolderSet<Block> replaceableBlocks;

	@WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/Column;scan(Lnet/minecraft/world/level/LevelSimulatedReader;Lnet/minecraft/core/BlockPos;ILjava/util/function/Predicate;Ljava/util/function/Predicate;)Ljava/util/Optional;"))
	private Optional<Column> alllowCustomBlock(LevelSimulatedReader level, BlockPos pos, int searchRange, Predicate<BlockState> insideColumn, Predicate<BlockState> validEdge, Operation<Optional<Column>> original) {
		Block block = (Object) this instanceof AdvancedLargeDripstoneFeature advanced ? advanced.dripstoneBlock.value().getState((LevelAccessor) level, ((LevelAccessor) level).getRandom(), pos).getBlock() : Blocks.DRIPSTONE_BLOCK;
		Predicate<BlockState> predicate = state -> SpeleothemUtils.isBaseOrLava(state, block, this.replaceableBlocks);
		return original.call(level, pos, searchRange, insideColumn, predicate);
	}

	@WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/feature/LargeDripstoneFeature;makeDripstone(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/util/RandomSource;ILnet/minecraft/util/valueproviders/FloatProvider;Lnet/minecraft/util/valueproviders/FloatProvider;)Lnet/minecraft/world/level/levelgen/feature/LargeDripstoneFeature$LargeDripstone;"))
	private LargeDripstoneFeature.LargeDripstone replaceWithAdvanced(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntness, FloatProvider heightScale, Operation<LargeDripstoneFeature.LargeDripstone> original) {
		if ((Object) this instanceof AdvancedLargeDripstoneFeature) {
			return new AdvancedLargeDripstoneFeature.LargeDripstone(root, pointingUp, radius, bluntness.sample(random), heightScale.sample(random));
		}
		else {
			return original.call(root, pointingUp, random, radius, bluntness, heightScale);
		}
	}

	@WrapOperation(method = "placeDebugMarkers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
	private boolean replaceWithAdvanced(BlockState instance, Object o, Operation<Boolean> original, @Local(argsOnly = true, name = "level") WorldGenLevel level, @Local(argsOnly = true, name = "origin") BlockPos origin) {
		if ((Object) this instanceof AdvancedLargeDripstoneFeature advanced) {
			return original.call(instance.is(advanced.dripstoneBlock.value().getState(level, level.getRandom(), origin).getBlock()));
		}
		else {
			return original.call(instance, o);
		}
	}

	@Mixin(LargeDripstoneFeature.LargeDripstone.class)
	public static class LargeDripstoneMixin {

		@WrapOperation(method = "placeBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
		private boolean placeCustomDripstone(WorldGenLevel instance, BlockPos blockPos, BlockState blockState, int i, Operation<Boolean> original) {
			if ((Object) this instanceof AdvancedLargeDripstoneFeature.AdvancedLargeDripstone advanced) {
				return original.call(instance, blockPos, SharedConstants.DEBUG_LARGE_DRIPSTONE ? Blocks.GLASS : advanced.dripstoneBlock.getState(instance, instance.getRandom(), blockPos), i);
			}
			else {
				return original.call(instance, blockPos, blockState, i);
			}
		}
	}
}
