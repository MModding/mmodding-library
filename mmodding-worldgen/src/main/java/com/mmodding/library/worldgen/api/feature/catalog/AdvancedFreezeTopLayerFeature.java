package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.block.api.catalog.UpsideSensitiveBlock;
import com.mmodding.library.worldgen.api.feature.catalog.configurations.AdvancedFreezeTopLayerConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class AdvancedFreezeTopLayerFeature extends Feature<AdvancedFreezeTopLayerConfiguration> {

	public AdvancedFreezeTopLayerFeature(Codec<AdvancedFreezeTopLayerConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedFreezeTopLayerConfiguration> context) {
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
							context.config().iceBlock.getState(context.level(), context.random(), blockPos),
							Block.UPDATE_CLIENTS
						);
					}

					if (biome.shouldSnow(world, abovePosition)) {
						world.setBlock(abovePosition, context.config().snowLayer.getState(context.level(), context.random(), abovePosition), Block.UPDATE_CLIENTS);
						BlockState state = world.getBlockState(blockPos);
						if (state.hasProperty(BlockStateProperties.SNOWY)) {
							world.setBlock(blockPos, state.setValue(BlockStateProperties.SNOWY, Boolean.TRUE), Block.UPDATE_CLIENTS);
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


}
