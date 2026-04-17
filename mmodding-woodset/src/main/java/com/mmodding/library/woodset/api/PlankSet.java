package com.mmodding.library.woodset.api;

import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.impl.PlankSetImpl;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

/**
 * Creates, stores, and registers a whole plank set with everything associated to it.
 */
public interface PlankSet {

	static PlankSet register(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return PlankSet.register(namespace, name, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, AutoMapper.identity());
	}

	static PlankSet register(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, AutoMapper<BlockBehaviour.Properties> patch) {
		return PlankSet.register(namespace, name, woodTypeBuilder, setTypeBuilder, EntityType::boatFactory, EntityType::chestBoatFactory, patch);
	}

	static PlankSet register(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory) {
		return PlankSet.register(namespace, name, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, AutoMapper.identity());
	}

	static PlankSet register(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		return new PlankSetImpl(namespace, name, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, patch);
	}

	Identifier getIdentifier();

	WoodType getWoodType();

	BlockRelatives getPlankRelatives();

	Block getHangingSign();

	Block getWallHangingSign();

	Block getShelf();

	EntityType<? extends AbstractBoat> getBoatEntityType();

	EntityType<? extends AbstractChestBoat> getChestBoatEntityType();

	Item getBoatItem();

	Item getChestBoatItem();

	interface BoatFactory {

		EntityType.EntityFactory<? extends AbstractBoat> make(Supplier<Item> boatItem);
	}

	interface ChestBoatFactory {

		EntityType.EntityFactory<? extends AbstractChestBoat> make(Supplier<Item> chestBoatItem);
	}
}
