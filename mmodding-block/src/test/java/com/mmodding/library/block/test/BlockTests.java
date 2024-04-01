package com.mmodding.library.block.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

public class BlockTests implements DefaultContentHolder {

	public static final Block FIRST_BLOCK = new Block(null);

	public static final Block SECOND_BLOCK = new Block(null);

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(init -> {
			FIRST_BLOCK.register(init.createId("first_block"));
			SECOND_BLOCK.register(init.createId("second_block"));
		});
	}
}
