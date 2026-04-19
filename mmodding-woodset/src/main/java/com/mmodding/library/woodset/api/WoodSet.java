package com.mmodding.library.woodset.api;

import com.mmodding.library.block.api.wrapper.BlockRelatives;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

/**
 * Stores a whole wood set with every non-levelgen thing associated to it.
 */
public interface WoodSet {

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
