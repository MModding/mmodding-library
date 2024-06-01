package com.mmodding.library.item.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;

public class BlockTests implements DefaultContentHolder {

	public static final Block FIRST_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.AIR)).withItem(new FabricItemSettings());

	public static final Block SECOND_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.AIR)).withItem(new FabricItemSettings());

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(pusher -> {
			FIRST_BLOCK.register(pusher.createId("first_block"));
			SECOND_BLOCK.register(pusher.createId("second_block"));
		});
	}
}
