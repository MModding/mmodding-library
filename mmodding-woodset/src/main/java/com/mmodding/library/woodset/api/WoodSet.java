package com.mmodding.library.woodset.api;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.impl.WoodSetImpl;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

/**
 * Creates, stores, and registers a whole wood set with every non-levelgen thing associated to it.
 */
public interface WoodSet {

	static WoodSet register(String namespace, String name, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, "log", "wood", true, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder);
	}

	static WoodSet registerNether(String namespace, String name, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, "stem", "hyphae", false, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder);
	}

	static WoodSet register(String namespace, String name, String log, String wood, boolean burnable, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSet.register(namespace, name, log, wood, burnable, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, AutoMapper.identity());
	}

	static WoodSet register(String namespace, String name, String log, String wood, boolean burnable, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, AutoMapper<BlockBehaviour.Properties> patch) {
		return WoodSet.register(namespace, name, log, wood, burnable, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, patch);
	}

	static WoodSet register(String namespace, String name, String log, String wood, boolean burnable, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory) {
		return WoodSet.register(namespace, name, log, wood, burnable, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, AutoMapper.identity());
	}

	static WoodSet register(String namespace, String name, String log, String wood, boolean burnable, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		return new WoodSetImpl(namespace, name, log, wood, burnable, logDisplay, leavesFactory, grower, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, patch);
	}

	Identifier getIdentifier();

	WoodType getWoodType();

	Block getLog();

	Block getWood();

	Block getStrippedLog();

	Block getStrippedWood();

	TagKey<Block> getLogsBlockTag();

	TagKey<Item> getLogsItemTag();

	boolean isBurnable();

	LogDisplay getLogDisplay();

	Block getLeaves();

	Block getSapling();

	Block getPottedSapling();

	BlockRelatives getPlankRelatives();

	Block getHangingSign();

	Block getWallHangingSign();

	Block getShelf();

	EntityType<? extends AbstractBoat> getBoatEntityType();

	EntityType<? extends AbstractChestBoat> getChestBoatEntityType();

	Item getBoatItem();

	Item getChestBoatItem();

	/**
	 * Indicates which model should be used for the log.
	 * This is useful for automated datagen.
	 */
	enum LogDisplay {
		NORMAL, // Like nether logs.
		WITH_HORIZONTAL, // Like most of vanilla logs.
		UV_LOCKED // Like cherry log and bamboo log.
	}

	interface BoatFactory {

		EntityType.EntityFactory<? extends AbstractBoat> make(Supplier<Item> boatItem);
	}

	interface ChestBoatFactory {

		EntityType.EntityFactory<? extends AbstractChestBoat> make(Supplier<Item> chestBoatItem);
	}
}
