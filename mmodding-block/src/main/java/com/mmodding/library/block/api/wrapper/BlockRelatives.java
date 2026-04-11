package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
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

	static BlockRelatives createWood(ResourceLocation identifier, WoodType type, AutoMapper<FabricBlockSettings> patch) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(identifier);
		FabricBlockSettings sharedSettings = patch.map((FabricBlockSettings) FabricBlockSettings.create().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD).ignitedByLava());
		return BlockRelatives.create(identifier, setType, sharedSettings, "_planks", Block::new)
				.push(BlockFamily.Variant.BUTTON, settings -> Blocks.woodenButton(setType))
				.push(BlockFamily.Variant.FENCE, settings -> new FenceBlock(settings.forceSolidOn()))
				.push(BlockFamily.Variant.FENCE_GATE, settings -> new FenceGateBlock(settings.forceSolidOn(), type))
				.push(BlockFamily.Variant.PRESSURE_PLATE, settings -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, settings.forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY), setType))
				.push(BlockFamily.Variant.SIGN, settings -> new StandingSignBlock(settings.forceSolidOn().noCollission(), type))
				.push(BlockFamily.Variant.WALL_SIGN, settings -> new WallSignBlock(settings.forceSolidOn().noCollission().dropsLike(BuiltInRegistries.BLOCK.get(IdentifierUtil.extend(identifier, "sign"))), type))
				.push(BlockFamily.Variant.SLAB, SlabBlock::new)
				.push(BlockFamily.Variant.STAIRS, settings -> new StairBlock(BuiltInRegistries.BLOCK.get(IdentifierUtil.extend(identifier, "planks")).defaultBlockState(), settings))
				.push(BlockFamily.Variant.DOOR, settings -> new DoorBlock(settings.noOcclusion(), setType))
				.push(BlockFamily.Variant.TRAPDOOR, settings -> new TrapDoorBlock(settings.noOcclusion().isValidSpawn(Blocks::never), setType));
	}

	static BlockRelatives createStone(ResourceLocation identifier, AutoMapper<FabricBlockSettings> patch, boolean hasPressurePlate, boolean hasButton) {
		return BlockRelatives.createStone(identifier, false, patch, hasPressurePlate, hasButton);
	}

	static BlockRelatives createStone(ResourceLocation identifier, boolean pluralOnMain, AutoMapper<FabricBlockSettings> patch, boolean hasPressurePlate, boolean hasButton) {
		BlockSetType setType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(identifier);
		FabricBlockSettings sharedSettings = patch.map((FabricBlockSettings) FabricBlockSettings.create().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f));
		BlockRelatives result = BlockRelatives.create(identifier, setType, sharedSettings, pluralOnMain ? "s" : "", Block::new)
			.push(BlockFamily.Variant.SLAB, SlabBlock::new)
			.push(BlockFamily.Variant.STAIRS, settings -> new StairBlock(BuiltInRegistries.BLOCK.get(identifier).defaultBlockState(), settings))
			.push(BlockFamily.Variant.WALL, WallBlock::new);
		if (hasPressurePlate) {
			result.push(BlockFamily.Variant.PRESSURE_PLATE, settings -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, settings.forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY), setType));
		}
		if (hasButton) {
			result.push(BlockFamily.Variant.BUTTON, settings -> new ButtonBlock(FabricBlockSettings.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), setType, 20, false));
		}
		return result;
	}

	static <T extends Block> BlockRelatives create(ResourceLocation identifier, BlockSetType setType, FabricBlockSettings sharedSettings, BlockFactory<T> mainFactory) {
		return BlockRelatives.create(identifier, setType, sharedSettings, "", mainFactory);
	}

	static <T extends Block> BlockRelatives create(ResourceLocation identifier, BlockSetType setType, FabricBlockSettings sharedSettings, String mainSuffix, BlockFactory<T> mainFactory) {
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
