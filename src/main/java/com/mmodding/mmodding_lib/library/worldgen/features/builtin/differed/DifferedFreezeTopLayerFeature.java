package com.mmodding.mmodding_lib.library.worldgen.features.builtin.differed;

import com.mmodding.mmodding_lib.library.blocks.CustomInfluenceableBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class DifferedFreezeTopLayerFeature extends Feature<DifferedFreezeTopLayerFeature.Config> {

	public DifferedFreezeTopLayerFeature(Codec<DifferedFreezeTopLayerFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeatureContext<DifferedFreezeTopLayerFeature.Config> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		BlockPos.Mutable abovePosition = new BlockPos.Mutable();
		BlockPos.Mutable blockPos = new BlockPos.Mutable();

		for (int xOffset = 0; xOffset < 16; xOffset++) {
			for (int zOffset = 0; zOffset < 16; zOffset++) {
				int x = pos.getX() + xOffset;
				int z = pos.getZ() + zOffset;
				int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z);
				for (int yOffset = 0; yOffset < context.getConfig().depthCoverage.get(context.getRandom()); yOffset++) {
					abovePosition.set(x, y - yOffset, z);
					blockPos.set(abovePosition).move(Direction.DOWN, 1);
					Biome biome = world.getBiome(abovePosition).value();
					if (biome.canSetIce(world, blockPos, false)) {
						world.setBlockState(
							blockPos,
							context.getConfig().iceBlock.getBlockState(context.getRandom(), blockPos),
							Block.NOTIFY_LISTENERS
						);
					}

					if (biome.canSetSnow(world, abovePosition)) {
						world.setBlockState(abovePosition, context.getConfig().snowLayer.getBlockState(context.getRandom(), abovePosition), Block.NOTIFY_LISTENERS);
						BlockState state = world.getBlockState(blockPos);
						if (state.contains(SnowyBlock.SNOWY)) {
							world.setBlockState(blockPos, state.with(SnowyBlock.SNOWY, Boolean.TRUE), Block.NOTIFY_LISTENERS);
						}
						this.updateInfluencableBlock(state, world, new BlockPos(blockPos));
					}
				}
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private <E extends Enum<E> & StringIdentifiable> void updateInfluencableBlock(BlockState state, StructureWorldAccess world, BlockPos pos) {
		if (state.getBlock() instanceof CustomInfluenceableBlock<?>) {
			CustomInfluenceableBlock<E> influenceable = (CustomInfluenceableBlock<E>) state.getBlock();
			world.setBlockState(
				pos,
				state.with(
					influenceable.getInfluenceProperty(),
					influenceable.getInfluence(world.getBlockState(new BlockPos(pos).up()))
				),
				Block.NOTIFY_LISTENERS
			);
		}
	}

	public static class Config implements FeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.TYPE_CODEC.fieldOf("ice_block").forGetter(config -> config.iceBlock),
			BlockStateProvider.TYPE_CODEC.fieldOf("snow_layer").forGetter(config -> config.snowLayer),
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
