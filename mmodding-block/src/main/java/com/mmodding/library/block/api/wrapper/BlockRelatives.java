package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import java.util.List;

public interface BlockRelatives {

	static BlockRelatives createWood(Identifier identifier, WoodType type, AutoMapper<Block.Properties> patch) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(identifier);
		Block.Properties sharedProperties = patch.map(Block.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD).ignitedByLava());
		return BlockRelatives.create(identifier, setType, sharedProperties, "_planks", Block::new)
				.register(BlockFamily.Variant.BUTTON, properties -> new ButtonBlock(setType, 30, Blocks.buttonProperties()))
				.register(BlockFamily.Variant.FENCE, properties -> new FenceBlock(properties.forceSolidOn()))
				.register(BlockFamily.Variant.FENCE_GATE, properties -> new FenceGateBlock(type, properties.forceSolidOn()))
				.register(BlockFamily.Variant.PRESSURE_PLATE, properties -> new PressurePlateBlock(setType, properties.forceSolidOn().noCollision().pushReaction(PushReaction.DESTROY)))
				.register(BlockFamily.Variant.SIGN, properties -> new StandingSignBlock(type, properties.forceSolidOn().noCollision()))
				.register(BlockFamily.Variant.WALL_SIGN, properties -> new WallSignBlock(type, properties.forceSolidOn().noCollision().overrideLootTable(BuiltInRegistries.BLOCK.get(IdentifierUtil.extend(identifier, "sign")).orElseThrow().value().getLootTable())))
				.register(BlockFamily.Variant.SLAB, SlabBlock::new)
				.register(BlockFamily.Variant.STAIRS, properties -> new StairBlock(BuiltInRegistries.BLOCK.get(IdentifierUtil.extend(identifier, "planks")).orElseThrow().value().defaultBlockState(), properties))
				.register(BlockFamily.Variant.DOOR, properties -> new DoorBlock(setType, properties.noOcclusion()))
				.register(BlockFamily.Variant.TRAPDOOR, properties -> new TrapDoorBlock(setType, properties.noOcclusion().isValidSpawn(Blocks::never)));
	}

	static BlockRelatives createStone(Identifier identifier, AutoMapper<Block.Properties> patch, boolean hasPressurePlate, boolean hasButton) {
		return BlockRelatives.createStone(identifier, false, patch, hasPressurePlate, hasButton);
	}

	static BlockRelatives createStone(Identifier identifier, boolean pluralOnMain, AutoMapper<Block.Properties> patch, boolean hasPressurePlate, boolean hasButton) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(identifier);
		Block.Properties sharedProperties = patch.map(Block.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f));
		BlockRelatives result = BlockRelatives.create(identifier, setType, sharedProperties, pluralOnMain ? "s" : "", Block::new)
			.register(BlockFamily.Variant.SLAB, SlabBlock::new)
			.register(BlockFamily.Variant.STAIRS, properties -> new StairBlock(BuiltInRegistries.BLOCK.get(identifier).orElseThrow().value().defaultBlockState(), properties))
			.register(BlockFamily.Variant.WALL, WallBlock::new);
		if (hasPressurePlate) {
			result.register(BlockFamily.Variant.PRESSURE_PLATE, properties -> new PressurePlateBlock(setType, properties.forceSolidOn().noCollision().pushReaction(PushReaction.DESTROY)));
		}
		if (hasButton) {
			result.register(BlockFamily.Variant.BUTTON, properties -> new ButtonBlock(setType, 20, Blocks.buttonProperties()));
		}
		return result;
	}

	static <T extends Block> BlockRelatives create(Identifier identifier, BlockSetType setType, Block.Properties sharedProperties, BlockFactory<T> mainFactory) {
		return BlockRelatives.create(identifier, setType, sharedProperties, "", mainFactory);
	}

	static <T extends Block> BlockRelatives create(Identifier identifier, BlockSetType setType, Block.Properties sharedProperties, String mainSuffix, BlockFactory<T> mainFactory) {
		return new BlockRelativesImpl(identifier, setType, sharedProperties, mainSuffix, mainFactory);
	}

	BlockSetType getSetType();

	Block getMain();

	Block get(BlockFamily.Variant variant);

	<T extends Block> BlockRelatives register(BlockFamily.Variant variant, BlockFactory<T> factory);

	TagKey<Block> getBlockTagKey();

	TagKey<Item> getItemTagKey();

	List<Block> getEntries();
}
