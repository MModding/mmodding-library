package com.mmodding.library.worldgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class AdvancedLiquidVegetationPatchFeature extends Feature<AdvancedLiquidVegetationPatchFeature.Config> {

	public AdvancedLiquidVegetationPatchFeature(Codec<AdvancedLiquidVegetationPatchFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		WorldGenLevel world = context.level();
		Config config = context.config();
		RandomSource random = context.random();
		BlockPos pos = context.origin();
		Predicate<BlockState> predicate = state -> state.is(config.replaceable);
		Set<BlockPos> positions = this.placeGroundAndGetPositions(world, config, random, pos, predicate, config.xzRadius.sample(random) + 1, config.xzRadius.sample(random) + 1);
		this.generateVegetation(context, world, config, random, positions);
		return !positions.isEmpty();
	}

	protected Set<BlockPos> placeGroundAndGetPositions(WorldGenLevel world, AdvancedLiquidVegetationPatchFeature.Config config, RandomSource random, BlockPos pos, Predicate<BlockState> replaceable, int radiusX, int radiusZ) {
		BlockPos.MutableBlockPos mutable = pos.mutable();
		BlockPos.MutableBlockPos result = mutable.mutable();
		Direction direction = config.surface.getDirection();
		Direction opposite = direction.getOpposite();
		Set<BlockPos> positions = new HashSet<>();

		for (int i = -radiusX; i <= radiusX; i++) {
			boolean xCheck = i == -radiusX || i == radiusX;

			for (int j = -radiusZ; j <= radiusZ; j++) {
				boolean zCheck = j == -radiusZ || j == radiusZ;
				boolean or = xCheck || zCheck;
				boolean equal = xCheck && zCheck;
				boolean xor = or && !equal;

				if (!equal && (!xor || config.extraEdgeColumnChance != 0.0f && !(random.nextFloat() > config.extraEdgeColumnChance))) {
					mutable.setWithOffset(pos, i, 0, j);

					for (int k = 0; world.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::isAir) && k < config.verticalRange; k++) {
						mutable.move(direction);
					}

					for (int l = 0; world.isStateAtPosition(mutable, state -> !state.isAir()) && l < config.verticalRange; l++) {
						mutable.move(opposite);
					}

					result.setWithOffset(mutable, config.surface.getDirection());
					BlockState blockState = world.getBlockState(result);
					if (world.isEmptyBlock(mutable) && blockState.isFaceSturdy(world, result, config.surface.getDirection().getOpposite())) {
						int depth = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0f && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
						BlockPos blockPos = result.immutable();
						if (this.placeGround(world, config, replaceable, random, result, depth)) {
							positions.add(blockPos);
						}
					}
				}
			}
		}

		Set<BlockPos> liquidPositions = new HashSet<>();
		BlockPos.MutableBlockPos liquidMutable = new BlockPos.MutableBlockPos();

		for(BlockPos blockPos : positions) {
			if (!isSolidBlockAroundPos(world, blockPos, liquidMutable)) {
				liquidPositions.add(blockPos);
			}
		}

		for(BlockPos blockPos : liquidPositions) {
			world.setBlock(blockPos, config.liquidState.getState(random, blockPos), Block.UPDATE_CLIENTS);
		}

		return liquidPositions.stream().map(BlockPos::above).filter(blockPos -> world.isStateAtPosition(blockPos, BlockState::isAir)).collect(Collectors.toSet());
	}

	protected void generateVegetation(FeaturePlaceContext<AdvancedLiquidVegetationPatchFeature.Config> context, WorldGenLevel world, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> positions) {
		for(BlockPos pos : positions) {
			if (config.vegetationChance > 0.0f && random.nextFloat() < config.vegetationChance) {
				config.vegetationFeature.value().placeWithBiomeCheck(world, context.chunkGenerator(), random, pos.below().relative(config.surface.getDirection().getOpposite()));
			}
		}
	}

	protected boolean placeGround(WorldGenLevel world, Config config, Predicate<BlockState> replaceable, RandomSource random, BlockPos.MutableBlockPos pos, int depth) {
		for (int i = 0; i < depth; ++i) {
			BlockState groundState = config.groundState.getState(random, pos);
			BlockState currentState = world.getBlockState(pos);
			if (!groundState.is(currentState.getBlock())) {
				if (!replaceable.test(currentState)) {
					return i != 0;
				}
				world.setBlock(pos, groundState, Block.UPDATE_CLIENTS);
				pos.move(config.surface.getDirection());
			}
		}
		return true;
	}

	private static boolean isSolidBlockAroundPos(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutablePos) {
		return isSolidBlockSide(world, pos, mutablePos, Direction.NORTH)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.EAST)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.SOUTH)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.WEST)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.DOWN);
	}

	private static boolean isSolidBlockSide(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutablePos, Direction direction) {
		mutablePos.setWithOffset(pos, direction);
		return !world.getBlockState(mutablePos).isFaceSturdy(world, mutablePos, direction.getOpposite());
	}

	public static class Config extends VegetationPatchConfiguration {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(config -> config.replaceable),
					BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(config -> config.groundState),
					BlockStateProvider.CODEC.fieldOf("liquid_state").forGetter(config -> config.liquidState),
					PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(config -> config.vegetationFeature),
					CaveSurface.CODEC.fieldOf("surface").forGetter(config -> config.surface),
					IntProvider.codec(1, 128).fieldOf("depth").forGetter(config -> config.depth),
					Codec.floatRange(0.0f, 1.0f).fieldOf("extra_bottom_block_chance").forGetter(config -> config.extraBottomBlockChance),
					Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(config -> config.verticalRange),
					Codec.floatRange(0.0f, 1.0f).fieldOf("vegetation_chance").forGetter(config -> config.vegetationChance),
					IntProvider.CODEC.fieldOf("xz_radius").forGetter(config -> config.xzRadius),
					Codec.floatRange(0.0f, 1.0f).fieldOf("extra_edge_column_chance").forGetter(config -> config.extraEdgeColumnChance)
				)
				.apply(instance, Config::new)
		);

		private final BlockStateProvider liquidState;

		public Config(TagKey<Block> replaceable, BlockStateProvider groundState, BlockStateProvider liquidState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider horizontalRadius, float extraEdgeColumnChance) {
			super(replaceable, groundState, vegetationFeature, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, horizontalRadius, extraEdgeColumnChance);
			this.liquidState = liquidState;
		}
	}
}
