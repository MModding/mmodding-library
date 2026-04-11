package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.block.api.catalog.UpsideSensitiveBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedFreezeTopLayerFeature extends Feature<AdvancedFreezeTopLayerFeature.Config> {

	public AdvancedFreezeTopLayerFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		WorldGenLevel world = context.level();
		BlockPos pos = context.origin();
		BlockPos.MutableBlockPos abovePosition = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

		for (int xOffset = 0; xOffset < 16; xOffset++) {
			for (int zOffset = 0; zOffset < 16; zOffset++) {
				int x = pos.getX() + xOffset;
				int z = pos.getZ() + zOffset;
				int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				for (int yOffset = 0; yOffset < context.config().depthCoverage.sample(context.random()); yOffset++) {
					abovePosition.set(x, y - yOffset, z);
					blockPos.set(abovePosition).move(Direction.DOWN, 1);
					Biome biome = world.getBiome(abovePosition).value();
					if (biome.shouldFreeze(world, blockPos, false)) {
						world.setBlock(
							blockPos,
							context.config().iceBlock.getState(context.random(), blockPos),
							Block.UPDATE_CLIENTS
						);
					}

					if (biome.shouldSnow(world, abovePosition)) {
						world.setBlock(abovePosition, context.config().snowLayer.getState(context.random(), abovePosition), Block.UPDATE_CLIENTS);
						BlockState state = world.getBlockState(blockPos);
						if (state.hasProperty(SnowyDirtBlock.SNOWY)) {
							world.setBlock(blockPos, state.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), Block.UPDATE_CLIENTS);
						}
						this.updateUpsideSensitiveBlock(state, world, new BlockPos(blockPos));
					}
				}
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private <E extends Enum<E> & StringRepresentable> void updateUpsideSensitiveBlock(BlockState state, WorldGenLevel world, BlockPos pos) {
		if (state.getBlock() instanceof UpsideSensitiveBlock<?>) {
			UpsideSensitiveBlock<E> upsideSensitive = (UpsideSensitiveBlock<E>) state.getBlock();
			world.setBlock(
				pos,
				state.setValue(
					upsideSensitive.getInfluenceProperty(),
					upsideSensitive.getInfluence(world.getBlockState(new BlockPos(pos).above()))
				),
				Block.UPDATE_CLIENTS
			);
		}
	}

	public static class Config implements FeatureConfiguration {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.CODEC.fieldOf("ice_block").forGetter(config -> config.iceBlock),
			BlockStateProvider.CODEC.fieldOf("snow_layer").forGetter(config -> config.snowLayer),
			IntProvider.POSITIVE_CODEC.fieldOf("depth_coverage").forGetter(config -> config.depthCoverage)
		).apply(instance, Config::new));

		private final BlockStateProvider iceBlock;
		private final BlockStateProvider snowLayer;
		private final IntProvider depthCoverage;

		public Config(BlockStateProvider iceBlock, BlockStateProvider snowLayer, IntProvider depthCoverage) {
			this.iceBlock = iceBlock;
			this.snowLayer = snowLayer;
			this.depthCoverage = depthCoverage;
		}
	}
}