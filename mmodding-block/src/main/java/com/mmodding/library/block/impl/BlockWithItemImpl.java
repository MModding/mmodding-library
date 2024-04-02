package com.mmodding.library.block.impl;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockWithItemImpl {

	public static Item getItem(Block block) {
		return ((BlockWithItemImpl.Getter) block).mmodding$getItem();
	}

	public interface Getter {

		Item mmodding$getItem();
	}
}
