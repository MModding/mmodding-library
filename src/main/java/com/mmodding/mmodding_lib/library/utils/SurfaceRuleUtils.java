package com.mmodding.mmodding_lib.library.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.SurfaceRules;

public class SurfaceRuleUtils {

	public static ImmutableList.Builder<SurfaceRules.MaterialRule> createBuilder(SurfaceRules.MaterialRule... rules) {
		ImmutableList.Builder<SurfaceRules.MaterialRule> builder = new ImmutableList.Builder<>();
		builder.add(rules);
		return builder;
	}

	public static SurfaceRules.MaterialRule getBlock(Block block) {
		return SurfaceRules.block(block.getDefaultState());
	}

	public static SurfaceRules.MaterialCondition onCeiling() {
		return SurfaceRules.ON_CEILING;
	}

	public static SurfaceRules.MaterialCondition onFloor() {
		return SurfaceRules.ON_FLOOR;
	}

	public static SurfaceRules.MaterialCondition water() {
		return SurfaceRules.water(0, 0);
	}

	public static SurfaceRules.MaterialCondition underWater() {
		return SurfaceRules.water(-1, 0);
	}

	public static SurfaceRules.MaterialCondition waterWithStoneDepth() {
		return SurfaceRules.waterWithStoneDepth(-6, -1);
	}

	public static SurfaceRules.MaterialRule getConditionalBlock(SurfaceRules.MaterialCondition condition, Block firstPrimary, Block secondPrimary) {
		return SurfaceRules.sequence(SurfaceRules.condition(condition, SurfaceRuleUtils.getBlock(firstPrimary)), SurfaceRuleUtils.getBlock(secondPrimary));
	}

	public static SurfaceRules.MaterialRule getBedrockRoof() {
		return SurfaceRules.condition(SurfaceRules.not(
			SurfaceRules.verticalGradient("bedrock_roof", YOffset.belowTop(5), YOffset.getTop())
		), SurfaceRuleUtils.getBlock(Blocks.BEDROCK));
	}

	public static SurfaceRules.MaterialRule getDeep(String randomName, Block deepBlock) {
		return SurfaceRules.condition(SurfaceRules.verticalGradient(
			randomName,
			YOffset.fixed(0),
			YOffset.fixed(8)
		), SurfaceRuleUtils.getBlock(deepBlock));
	}

	public static SurfaceRules.MaterialRule getBedrockFloor() {
		return SurfaceRules.condition(SurfaceRules.not(
			SurfaceRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5))
		), SurfaceRuleUtils.getBlock(Blocks.BEDROCK));
	}
}
