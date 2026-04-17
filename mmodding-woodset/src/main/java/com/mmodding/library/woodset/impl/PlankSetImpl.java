package com.mmodding.library.woodset.impl;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.api.PlankSet;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@ApiStatus.Internal
public class PlankSetImpl implements PlankSet {

	private final Identifier identifier;
	private final WoodType type;
	private final BlockRelatives plankRelatives;
	private final Block hangingSign;
	private final Block wallHangingSign;
	private final Block shelf;
	private final EntityType<? extends AbstractBoat> boatEntityType;
	private final EntityType<? extends AbstractChestBoat> chestBoatEntityType;
	private final Item boatItem;
	private final Item chestBoatItem;

	public PlankSetImpl(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		this.identifier = Identifier.fromNamespaceAndPath(namespace, name);
		this.type = woodTypeBuilder.build(this.identifier, setTypeBuilder.register(this.identifier));
		this.plankRelatives = BlockRelatives.registerPlanks(this.identifier, this.type, patch);
		this.hangingSign = this.registerBlock("_hanging_sign", properties -> new CeilingHangingSignBlock(this.type, properties), patch);
		this.wallHangingSign = this.registerBlock("_hangign_sign_wall", properties -> new WallHangingSignBlock(this.type, properties), patch);
		BlockEntityType.HANGING_SIGN.addValidBlock(this.hangingSign);
		BlockEntityType.HANGING_SIGN.addValidBlock(this.wallHangingSign);
		Items.registerBlock(this.hangingSign, (block, properties) -> new HangingSignItem(block, this.wallHangingSign, properties), new Item.Properties().stacksTo(16));
		this.shelf = this.registerBlock("_shelf", ShelfBlock::new, patch).registerItem();
		this.boatEntityType = this.registerEntityType("_boat", EntityType.Builder.of(boatFactory.make(this::getBoatItem), MobCategory.MISC).noLootTable().sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
		this.chestBoatEntityType = this.registerEntityType("_chest_boat", EntityType.Builder.of(chestBoatFactory.make(this::getChestBoatItem), MobCategory.MISC).noLootTable().sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
		this.boatItem = this.registerItem("_boat", properties -> new BoatItem(this.boatEntityType, properties), new Item.Properties().stacksTo(1));
		this.chestBoatItem = this.registerItem("_chest_boat", properties -> new BoatItem(this.chestBoatEntityType, properties), new Item.Properties().stacksTo(1));
	}

	protected <T extends Block> Block registerBlock(String suffix, BlockFactory<T> factory, AutoMapper<BlockBehaviour.Properties> patch) {
		return this.registerBlock("", suffix, factory, patch);
	}

	protected <T extends Block> Block registerBlock(String prefix, String suffix, BlockFactory<T> factory, AutoMapper<BlockBehaviour.Properties> patch) {
		Block.Properties properties = patch.map(Block.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD).ignitedByLava());
		return Blocks.register(ResourceKey.create(Registries.BLOCK, this.identifier.withPath(path -> prefix + path + suffix)), factory::make, properties);
	}

	private Item registerItem(String suffix, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, this.identifier.withPath(path -> path + suffix));
		return Items.registerItem(key, factory, properties);
	}

	private <T extends Entity> EntityType<T> registerEntityType(String suffix, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, this.identifier.withPath(path -> path + suffix));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public WoodType getWoodType() {
		return this.type;
	}

	@Override
	public BlockRelatives getPlankRelatives() {
		return this.plankRelatives;
	}

	@Override
	public Block getHangingSign() {
		return this.hangingSign;
	}

	@Override
	public Block getWallHangingSign() {
		return this.wallHangingSign;
	}

	@Override
	public Block getShelf() {
		return this.shelf;
	}

	@Override
	public EntityType<? extends AbstractBoat> getBoatEntityType() {
		return this.boatEntityType;
	}

	@Override
	public EntityType<? extends AbstractChestBoat> getChestBoatEntityType() {
		return this.chestBoatEntityType;
	}

	@Override
	public Item getBoatItem() {
		return this.boatItem;
	}

	@Override
	public Item getChestBoatItem() {
		return this.chestBoatItem;
	}

	static {
		DataContentResolver.<PlankSetImpl, BlockFamily>register(PlankSetImpl.class, BlockFamily.class, input -> List.of(((BlockRelativesImpl) input.getPlankRelatives()).initDataFamily()));
		DataContentResolver.<PlankSetImpl, Block>register(PlankSetImpl.class, Block.class, input -> {
			List<Block> output = new ArrayList<>();
			output.add(input.hangingSign);
			output.add(input.wallHangingSign);
			output.add(input.shelf);
			return output;
		});
		DataContentResolver.<PlankSetImpl, Item>register(PlankSetImpl.class, Item.class, input -> {
			List<Item> output = new ArrayList<>();
			output.add(input.boatItem);
			output.add(input.chestBoatItem);
			return output;
		});
		DataContentResolver.<PlankSetImpl, EntityType<?>>register(PlankSetImpl.class, EntityType.class, input -> {
			List<EntityType<?>> output = new ArrayList<>();
			output.add(input.boatEntityType);
			output.add(input.chestBoatEntityType);
			return output;
		});
	}
}
