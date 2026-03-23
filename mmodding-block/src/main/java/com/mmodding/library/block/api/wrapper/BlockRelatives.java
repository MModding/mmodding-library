package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.List;

public interface BlockRelatives {

	static BlockRelatives createWood(Identifier identifier, WoodType type, AutoMapper<FabricBlockSettings> patch) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(identifier);
		FabricBlockSettings sharedSettings = patch.map(FabricBlockSettings.create().instrument(Instrument.BASS).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).burnable());
		return BlockRelatives.create(identifier, setType, sharedSettings, "_planks", Block::new)
				.push(BlockFamily.Variant.BUTTON, settings -> Blocks.createWoodenButtonBlock(setType))
				.push(BlockFamily.Variant.FENCE, settings -> new FenceBlock(settings.solid()))
				.push(BlockFamily.Variant.FENCE_GATE, settings -> new FenceGateBlock(settings.solid(), type))
				.push(BlockFamily.Variant.PRESSURE_PLATE, settings -> new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, settings.solid().noCollision().pistonBehavior(PistonBehavior.DESTROY), setType))
				.push(BlockFamily.Variant.SIGN, settings -> new SignBlock(settings.solid().noCollision(), type))
				.push(BlockFamily.Variant.WALL_SIGN, settings -> new WallSignBlock(settings.solid().noCollision().dropsLike(Registries.BLOCK.get(IdentifierUtil.extend(identifier, "sign"))), type))
				.push(BlockFamily.Variant.SLAB, SlabBlock::new)
				.push(BlockFamily.Variant.STAIRS, settings -> new StairsBlock(Registries.BLOCK.get(IdentifierUtil.extend(identifier, "planks")).getDefaultState(), settings))
				.push(BlockFamily.Variant.DOOR, settings -> new DoorBlock(settings.nonOpaque(), setType))
				.push(BlockFamily.Variant.TRAPDOOR, settings -> new TrapdoorBlock(settings.nonOpaque().allowsSpawning(Blocks::never), setType));
	}

	static BlockRelatives createStone(Identifier identifier, AutoMapper<FabricBlockSettings> patch, boolean hasPressurePlate, boolean hasButton) {
		return BlockRelatives.createStone(identifier, false, patch, hasPressurePlate, hasButton);
	}

	static BlockRelatives createStone(Identifier identifier, boolean pluralOnMain, AutoMapper<FabricBlockSettings> patch, boolean hasPressurePlate, boolean hasButton) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(identifier);
		FabricBlockSettings sharedSettings = patch.map(FabricBlockSettings.create().instrument(Instrument.BASEDRUM).requiresTool().strength(1.5f, 6.0f));
		BlockRelatives result = BlockRelatives.create(identifier, setType, sharedSettings, pluralOnMain ? "s" : "", Block::new)
				.push(BlockFamily.Variant.SLAB, SlabBlock::new)
				.push(BlockFamily.Variant.STAIRS, settings -> new StairsBlock(Registries.BLOCK.get(identifier).getDefaultState(), settings));
		if (hasPressurePlate) {
			result.push(BlockFamily.Variant.PRESSURE_PLATE, settings -> new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, settings.solid().noCollision().pistonBehavior(PistonBehavior.DESTROY), setType));
		}
		if (hasButton) {
			result.push(BlockFamily.Variant.BUTTON, settings -> new ButtonBlock(FabricBlockSettings.create().noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY), setType, 20, false));
		}
		return result;
	}

	static <T extends Block> BlockRelatives create(Identifier identifier, BlockSetType setType, FabricBlockSettings sharedSettings, BlockFactory<T> mainFactory) {
		return BlockRelatives.create(identifier, setType, sharedSettings, "", mainFactory);
	}

	static <T extends Block> BlockRelatives create(Identifier identifier, BlockSetType setType, FabricBlockSettings sharedSettings, String mainSuffix, BlockFactory<T> mainFactory) {
		return new BlockRelativesImpl(identifier, setType, sharedSettings, mainSuffix, mainFactory);
	}

	BlockSetType getSetType();

	Block getMain();

	Block get(BlockFamily.Variant variant);

	<T extends Block> BlockRelatives push(BlockFamily.Variant variant, BlockFactory<T> factory);

	TagKey<Block> getBlockTagKey();

	TagKey<Item> getItemTagKey();

	List<Block> getEntries();

	void register();

	@Environment(EnvType.CLIENT)
	void cutoutMain();

	@Environment(EnvType.CLIENT)
	void translucentMain();

	@Environment(EnvType.CLIENT)
	void cutoutVariant(BlockFamily.Variant variant);

	@Environment(EnvType.CLIENT)
	void translucentVariant(BlockFamily.Variant variant);

	@Environment(EnvType.CLIENT)
	void cutoutAll();

	@Environment(EnvType.CLIENT)
	void translucentAll();
}
