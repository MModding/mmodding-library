package com.mmodding.library.sublevel.test;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Set;

public class TeleportItem extends Item {

	public TeleportItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level instanceof ServerLevel serverLevel) {
			ServerLevel sub = SubLevelTests.PLAYER_ATTACHED.getOrCreate(serverLevel, player);
			player.teleportTo(sub, 1.0, 80.0, 1.0, Set.of(), 1.0f, 1.0f, false);
		}
		return super.use(level, player, hand);
	}
}
