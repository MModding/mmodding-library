package com.mmodding.library.woodset.impl;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.api.WoodSet;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Internal
public class WoodSetImpl implements WoodSet {

	private final Identifier identifier;
	private final WoodType type;

	private final Block log;
	private final Block wood;
	private final Block strippedLog;
	private final Block strippedWood;
	private final TagKey<Block> logsBlockTag;
	private final TagKey<Item> logsItemTag;
	private final boolean burnable;
	private final LogDisplay logDisplay;
	private final Block leaves;
	private final Block sapling;
	private final Block pottedSapling;

	private final BlockRelatives plankRelatives;
	private final Block hangingSign;
	private final Block wallHangingSign;
	private final Block shelf;
	private final EntityType<? extends AbstractBoat> boatEntityType;
	private final EntityType<? extends AbstractChestBoat> chestBoatEntityType;
	private final Item boatItem;
	private final Item chestBoatItem;

	public WoodSetImpl(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, String logName, String woodName, boolean burnable, SoundType woodSoundType, LogDisplay logDisplay, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, SoundType leavesSoundType, TreeGrower grower, SoundType saplingSoundType, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		this.identifier = Identifier.fromNamespaceAndPath(namespace, name);
		this.type = woodTypeBuilder.build(this.identifier, setTypeBuilder.register(this.identifier));
		this.log = this.registerBlock("_" + logName, RotatedPillarBlock::new, properties -> patch.map(properties.sound(woodSoundType))).registerItem();
		this.wood = this.registerBlock("_" + woodName, RotatedPillarBlock::new, properties -> patch.map(properties.sound(woodSoundType))).registerItem();
		this.strippedLog = this.registerBlock("stripped_", "_" + logName, RotatedPillarBlock::new, properties -> patch.map(properties.sound(woodSoundType))).registerItem();
		this.strippedWood = this.registerBlock("stripped_", "_" + woodName, RotatedPillarBlock::new, properties -> patch.map(properties.sound(woodSoundType))).registerItem();
		StrippableBlockRegistry.register(this.log, this.strippedLog);
		StrippableBlockRegistry.register(this.wood, this.strippedWood);
		this.logsBlockTag = TagKey.create(Registries.BLOCK, this.identifier.withPath(path -> path + "_logs"));
		this.logsItemTag = TagKey.create(Registries.ITEM, this.identifier.withPath(path -> path + "_logs"));
		this.burnable = burnable;
		this.logDisplay = logDisplay;
		this.leaves = this.registerBlock("_leaves", leavesFactory, _ -> patch.map(Blocks.leavesProperties(leavesSoundType))).registerItem();
		BlockBehaviour.Properties saplingSettings = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(saplingSoundType).pushReaction(PushReaction.DESTROY);
		this.sapling = this.registerBlock("_sapling", properties -> new SaplingBlock(grower, properties), _ -> patch.map(saplingSettings)).registerItem();
		this.pottedSapling = this.registerBlock("potted_", "_sapling", properties -> new FlowerPotBlock(this.sapling, properties), patch);
		this.plankRelatives = BlockRelatives.registerPlanks(this.identifier, this.type, patch);
		this.hangingSign = this.registerBlock("_hanging_sign", properties -> new CeilingHangingSignBlock(this.type, properties), patch);
		this.wallHangingSign = this.registerBlock("_hangign_sign_wall", properties -> new WallHangingSignBlock(this.type, properties), patch);
		BlockEntityType.HANGING_SIGN.addValidBlock(this.hangingSign);
		BlockEntityType.HANGING_SIGN.addValidBlock(this.wallHangingSign);
		Items.registerBlock(this.hangingSign, (block, properties) -> new HangingSignItem(block, this.wallHangingSign, properties), new Item.Properties().stacksTo(16));
		this.shelf = this.registerBlock("_shelf", ShelfBlock::new, properties -> patch.map(properties.sound(SoundType.SHELF))).registerItem();
		BlockEntityType.SHELF.addValidBlock(this.shelf);
		this.boatEntityType = this.registerEntityType("_boat", EntityType.Builder.of(boatFactory.make(this::getBoatItem), MobCategory.MISC).noLootTable().sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
		this.chestBoatEntityType = this.registerEntityType("_chest_boat", EntityType.Builder.of(chestBoatFactory.make(this::getChestBoatItem), MobCategory.MISC).noLootTable().sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
		this.boatItem = this.registerItem("_boat", properties -> new BoatItem(this.boatEntityType, properties), new Item.Properties().stacksTo(1));
		this.chestBoatItem = this.registerItem("_chest_boat", properties -> new BoatItem(this.chestBoatEntityType, properties), new Item.Properties().stacksTo(1));
	}

	private <T extends Block> Block registerBlock(String suffix, BlockFactory<T> factory, AutoMapper<BlockBehaviour.Properties> patch) {
		return this.registerBlock("", suffix, factory, patch);
	}

	private <T extends Block> Block registerBlock(String prefix, String suffix, BlockFactory<T> factory, AutoMapper<BlockBehaviour.Properties> patch) {
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
	public Block getLog() {
		return this.log;
	}

	@Override
	public Block getWood() {
		return this.wood;
	}

	@Override
	public Block getStrippedLog() {
		return this.strippedLog;
	}

	@Override
	public Block getStrippedWood() {
		return this.strippedWood;
	}

	@Override
	public TagKey<Block> getLogsBlockTag() {
		return this.logsBlockTag;
	}

	@Override
	public TagKey<Item> getLogsItemTag() {
		return this.logsItemTag;
	}

	@Override
	public boolean isBurnable() {
		return this.burnable;
	}

	@Override
	public LogDisplay getLogDisplay() {
		return this.logDisplay;
	}

	@Override
	public Block getLeaves() {
		return this.leaves;
	}

	@Override
	public Block getSapling() {
		return this.sapling;
	}

	@Override
	public Block getPottedSapling() {
		return this.pottedSapling;
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
}
