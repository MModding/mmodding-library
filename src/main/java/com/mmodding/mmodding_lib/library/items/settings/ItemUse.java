package com.mmodding.mmodding_lib.library.items.settings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ItemUse {

	void apply(World world, PlayerEntity user, Hand hand);
}
