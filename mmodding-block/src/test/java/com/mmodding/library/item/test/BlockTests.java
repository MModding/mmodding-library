package com.mmodding.library.item.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class BlockTests implements DefaultContentHolder {

	public static final Block FIRST_BLOCK = new Block(QuiltBlockSettings.copyOf(Blocks.AIR)).withItem(new QuiltItemSettings());

	public static final Block SECOND_BLOCK = new Block(QuiltBlockSettings.copyOf(Blocks.AIR)).withItem(new QuiltItemSettings());

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(init -> {
			FIRST_BLOCK.register(init.createId("first_block"));
			SECOND_BLOCK.register(init.createId("second_block"));
		});
	}
}
