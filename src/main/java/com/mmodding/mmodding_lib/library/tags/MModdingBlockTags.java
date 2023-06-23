package com.mmodding.mmodding_lib.library.tags;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class MModdingBlockTags {

	public static final TagKey<Block> OXIDIZABLE = TagKey.of(Registry.BLOCK_KEY, new MModdingIdentifier("oxidizable"));
	public static final TagKey<Block> ANIMAL_PATHFINDINGS = TagKey.of(Registry.BLOCK_KEY, new MModdingIdentifier("animal_pathfindings"));
}
