package com.mmodding.library.woodset.api;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.impl.WoodSetImpl;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Creates, stores, and registers a whole wood set with every non-levelgen thing associated to it.
 * It includes everything from a plank set.
 */
public interface WoodSet extends PlankSet {

	static WoodSet register(String namespace, String name, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, "log", "wood", leavesFactory, grower, woodTypeBuilder, setTypeBuilder);
	}

	static WoodSet registerNether(String namespace, String name, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, "stem", "hyphae", leavesFactory, grower, woodTypeBuilder, setTypeBuilder);
	}

	static WoodSet register(String namespace, String name, String log, String wood, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, log, wood, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, AutoMapper.identity());
	}

	static WoodSet register(String namespace, String name, String log, String wood, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, AutoMapper<BlockBehaviour.Properties> patch) {
		return WoodSet.register(namespace, name, log, wood, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, patch);
	}

	static WoodSet register(String namespace, String name, String log, String wood, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory) {
		return WoodSet.register(namespace, name, log, wood, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, AutoMapper.identity());
	}

	static WoodSet register(String namespace, String name, String log, String wood, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		return new WoodSetImpl(namespace, name, log, wood, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, patch);
	}

	Block getLog();

	Block getWood();

	Block getStrippedLog();

	Block getStrippedWood();

	Block getLeaves();

	Block getSapling();
}
