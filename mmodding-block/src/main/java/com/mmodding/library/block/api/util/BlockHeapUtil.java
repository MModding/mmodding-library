package com.mmodding.library.block.api.util;

import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.java.api.function.Mapper;
import com.mmodding.library.java.api.list.ListUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Utilities for {@link BlockHeap} creation.
 */
public class BlockHeapUtil {

	public static final List<String> COLORS = List.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black");

	public static Mapper<String, BlockBehaviour.Properties> mapForColors(Supplier<BlockBehaviour.Properties> base) {
		return constructor -> base.get().mapColor(switch (constructor) {
			case "white" -> MapColor.SNOW;
			case "orange" -> MapColor.COLOR_ORANGE;
			case "magenta" -> MapColor.COLOR_MAGENTA;
			case "light_blue" -> MapColor.COLOR_LIGHT_BLUE;
			case "yellow" -> MapColor.COLOR_YELLOW;
			case "lime" -> MapColor.COLOR_LIGHT_GREEN;
			case "pink" -> MapColor.COLOR_PINK;
			case "gray" -> MapColor.COLOR_GRAY;
			case "light_gray" -> MapColor.COLOR_LIGHT_GRAY;
			case "cyan" -> MapColor.COLOR_CYAN;
			case "purple" -> MapColor.COLOR_PURPLE;
			case "blue" -> MapColor.COLOR_BLUE;
			case "brown" -> MapColor.COLOR_BROWN;
			case "green" -> MapColor.COLOR_GREEN;
			case "red" -> MapColor.COLOR_RED;
			case "black" -> MapColor.COLOR_BLACK;
			default -> MapColor.NONE;
		});
	}

	public static final List<String> OVERWORLD_WOODS = List.of("acacia", "cherry", "jungle", "dark_oak", "pale_oak", "mangrove", "bamboo", "oak", "spruce", "birch");

	public static final List<String> NETHER_WOODS = List.of("crimson", "warped");

	public static final List<String> ALL_WOODS = ListUtil.builder(constructors -> { constructors.addAll(OVERWORLD_WOODS); constructors.addAll(NETHER_WOODS); });

	public static <T extends Block> BlockHeap.NameBlockFactory<T> setTypeForWoods(BiFunction<BlockSetType, BlockBehaviour.Properties, T> factory) {
		return (constructor, properties) -> factory.apply(switch (constructor) {
			case "acacia" -> BlockSetType.ACACIA;
			case "cherry" -> BlockSetType.CHERRY;
			case "jungle" -> BlockSetType.JUNGLE;
			case "dark_oak" -> BlockSetType.DARK_OAK;
			case "pale_oak" -> BlockSetType.PALE_OAK;
			case "mangrove" -> BlockSetType.MANGROVE;
			case "bamboo" -> BlockSetType.BAMBOO;
			case "oak" -> BlockSetType.OAK;
			case "spruce" -> BlockSetType.SPRUCE;
			case "birch" -> BlockSetType.BIRCH;
			default -> throw new IllegalArgumentException("Should be a recognized wood constructor");
		}, properties);
	}

	public static Mapper<String, BlockBehaviour.Properties> mapForWoods(Supplier<BlockBehaviour.Properties> base) {
		return constructor -> base.get().mapColor(switch (constructor) {
			case "acacia" -> MapColor.COLOR_ORANGE;
			case "cherry" -> MapColor.TERRACOTTA_WHITE;
			case "jungle" -> MapColor.DIRT;
			case "dark_oak" -> MapColor.COLOR_BROWN;
			case "pale_oak" -> MapColor.QUARTZ;
			case "mangrove" -> MapColor.COLOR_RED;
			case "bamboo" -> MapColor.COLOR_YELLOW;
			case "oak" -> MapColor.WOOD;
			case "spruce" -> MapColor.PODZOL;
			case "birch" -> MapColor.SAND;
			default -> MapColor.NONE;
		});
	}
}
