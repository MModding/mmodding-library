package com.mmodding.library.levelgen.api.feature.catalog;

import com.mmodding.library.block.api.catalog.UpsideSensitiveBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AdvancedSnowAndFreezeFeature(Holder<BlockStateProvider> snowLayer, Holder<BlockStateProvider> iceBlock) implements Feature {

	public static final MapCodec<AdvancedSnowAndFreezeFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("ice_block").forGetter(config -> config.iceBlock),
		BlockStateProvider.CODEC.fieldOf("snow_layer").forGetter(config -> config.snowLayer)
	).apply(instance, AdvancedSnowAndFreezeFeature::new));

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		BlockPos.MutableBlockPos topPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

		for (int dx = 0; dx < 16; dx++) {
			for (int dz = 0; dz < 16; dz++) {
				int x = origin.getX() + dx;
				int z = origin.getZ() + dz;
				int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				topPos.set(x, y, z);
				belowPos.set(topPos).move(Direction.DOWN, 1);
				Biome biome = level.getBiome(topPos).value();
				if (biome.shouldFreeze(level, belowPos, false)) {
					level.setBlock(belowPos, this.iceBlock.value().getState(level, random, belowPos), 2);
				}

				if (biome.shouldSnow(level, topPos)) {
					level.setBlock(topPos, this.snowLayer.value().getState(level, random, topPos), 2);
					BlockState belowState = level.getBlockState(belowPos);
					if (belowState.hasProperty(SnowyBlock.SNOWY)) {
						level.setBlock(belowPos, belowState.setValue(SnowyBlock.SNOWY, true), 2);
					}
					this.updateUpsideSensitiveBlock(belowState, level, belowPos.immutable());
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
					upsideSensitive.getInfluence(world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()).above()))
				),
				Block.UPDATE_CLIENTS
			);
		}
	}
}
