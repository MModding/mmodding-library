package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.impl.feature.helper.AdvancedDripstoneHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SmallDripstoneFeatureConfig;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

public class AdvancedSmallDripstoneFeature extends Feature<AdvancedSmallDripstoneFeature.Config> {

	public AdvancedSmallDripstoneFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<Config> context) {
		Block pointedDripstoneBlock = context.getConfig().pointedDripstoneBlock.get(context.getRandom(), context.getOrigin()).getBlock();
		Block dripstoneBlock = context.getConfig().dripstoneBlock.get(context.getRandom(), context.getOrigin()).getBlock();

		WorldAccess worldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		Random randomGenerator = context.getRandom();
		Config pointedDripstoneFeatureConfig = context.getConfig();
		Optional<Direction> optional = this.getDirection(worldAccess, blockPos, randomGenerator);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos patchPos = blockPos.offset(optional.get().getOpposite());
			this.generateDripstoneBlocksPatch(dripstoneBlock, worldAccess, randomGenerator, patchPos, pointedDripstoneFeatureConfig);
			int i = randomGenerator.nextFloat() < pointedDripstoneFeatureConfig.chanceOfTallerDripstone
				&& DripstoneHelper.canGenerate(worldAccess.getBlockState(blockPos.offset(optional.get())))
				? 2
				: 1;
			AdvancedDripstoneHelper.generatePointedDripstone(pointedDripstoneBlock, worldAccess, blockPos, optional.get(), i, false);
			return true;
		}
	}

	private Optional<Direction> getDirection(WorldAccess world, BlockPos pos, Random random) {
		boolean bl = DripstoneHelper.canReplace(world.getBlockState(pos.up()));
		boolean bl2 = DripstoneHelper.canReplace(world.getBlockState(pos.down()));
		if (bl && bl2) {
			return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (bl) {
			return Optional.of(Direction.DOWN);
		} else {
			return bl2 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void generateDripstoneBlocksPatch(Block dripstoneBlock, WorldAccess world, Random random, BlockPos pos, Config config) {
		AdvancedDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, pos);

		for(Direction direction : Direction.Type.HORIZONTAL) {
			if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
				BlockPos blockPos = pos.offset(direction);
				AdvancedDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos);
				if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
					BlockPos blockPos2 = blockPos.offset(Direction.random(random));
					AdvancedDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos2);
					if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
						BlockPos blockPos3 = blockPos2.offset(Direction.random(random));
						AdvancedDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos3);
					}
				}
			}
		}
	}

	public static class Config extends SmallDripstoneFeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.TYPE_CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
			BlockStateProvider.TYPE_CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(config -> config.chanceOfTallerDripstone),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(config -> config.chanceOfDirectionalSpread),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius2),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius3)
		).apply(instance, Config::new));

		private final BlockStateProvider pointedDripstoneBlock;
		private final BlockStateProvider dripstoneBlock;

		public Config(BlockStateProvider pointedDripstoneBlock, BlockStateProvider dripstoneBlock, float tallerDripstoneChance, float directionalSpreadChance, float spreadRadius2Chance, float spreadRadius3Chance) {
			super(tallerDripstoneChance, directionalSpreadChance, spreadRadius2Chance, spreadRadius3Chance);
			this.pointedDripstoneBlock = pointedDripstoneBlock;
			this.dripstoneBlock = dripstoneBlock;
		}
	}
}