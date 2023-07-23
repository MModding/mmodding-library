package com.mmodding.mmodding_lib.library.worldgen.features.differeds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PointedDripstoneFeatureConfig;
import com.mmodding.mmodding_lib.library.helpers.CustomDripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

public class DifferedPointedDripstoneFeature extends Feature<DifferedPointedDripstoneFeature.Config> {

	public DifferedPointedDripstoneFeature(Codec<DifferedPointedDripstoneFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeatureContext<DifferedPointedDripstoneFeature.Config> context) {

		Block pointedDripstoneBlock = context.getConfig().pointedDripstoneBlock.getBlockState(context.getRandom(), context.getOrigin()).getBlock();
		Block dripstoneBlock = context.getConfig().dripstoneBlock.getBlockState(context.getRandom(), context.getOrigin()).getBlock();

		WorldAccess worldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		RandomGenerator randomGenerator = context.getRandom();
		PointedDripstoneFeatureConfig pointedDripstoneFeatureConfig = context.getConfig();
		Optional<Direction> optional = this.getDirection(worldAccess, blockPos, randomGenerator);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos patchPos = blockPos.offset(optional.get().getOpposite());
			this.generateDripstoneBlocksPatch(dripstoneBlock, worldAccess, randomGenerator, patchPos, pointedDripstoneFeatureConfig);
			int i = randomGenerator.nextFloat() < pointedDripstoneFeatureConfig.tallerDripstoneChance
				&& CustomDripstoneHelper.canGenerate(worldAccess.getBlockState(blockPos.offset(optional.get())))
				? 2
				: 1;
			CustomDripstoneHelper.generatePointedDripstone(pointedDripstoneBlock, worldAccess, blockPos, optional.get(), i, false);
			return true;
		}
	}

	private Optional<Direction> getDirection(WorldAccess world, BlockPos pos, RandomGenerator random) {
		boolean bl = CustomDripstoneHelper.canReplace(world.getBlockState(pos.up()));
		boolean bl2 = CustomDripstoneHelper.canReplace(world.getBlockState(pos.down()));
		if (bl && bl2) {
			return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (bl) {
			return Optional.of(Direction.DOWN);
		} else {
			return bl2 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void generateDripstoneBlocksPatch(Block dripstoneBlock, WorldAccess world, RandomGenerator random, BlockPos pos, PointedDripstoneFeatureConfig config) {
		CustomDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, pos);

		for(Direction direction : Direction.Type.HORIZONTAL) {
			if (!(random.nextFloat() > config.directionalSpreadChance)) {
				BlockPos blockPos = pos.offset(direction);
				CustomDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos);
				if (!(random.nextFloat() > config.spreadRadius2Chance)) {
					BlockPos blockPos2 = blockPos.offset(Direction.random(random));
					CustomDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos2);
					if (!(random.nextFloat() > config.spreadRadius3Chance)) {
						BlockPos blockPos3 = blockPos2.offset(Direction.random(random));
						CustomDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, blockPos3);
					}
				}
			}
		}
	}

	public static class Config extends PointedDripstoneFeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.TYPE_CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
			BlockStateProvider.TYPE_CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(config -> config.tallerDripstoneChance),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(config -> config.directionalSpreadChance),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(config -> config.spreadRadius2Chance),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(config -> config.spreadRadius3Chance)
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
