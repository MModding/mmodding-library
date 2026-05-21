package com.mmodding.library.task.test;

import com.mmodding.library.task.api.RepeatingTask;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public record DiamondGiverTask(UUID playerUUID, int amount) implements RepeatingTask {

	public static final Codec<DiamondGiverTask> CODEC = RecordCodecBuilder.create(i -> i.group(
		Codec.STRING.xmap(UUID::fromString, UUID::toString).fieldOf("player_uuid").forGetter(DiamondGiverTask::playerUUID),
		Codec.INT.fieldOf("amount").forGetter(DiamondGiverTask::amount)
	).apply(i, DiamondGiverTask::new));

	@Override
	public int execute(MinecraftServer server) {
		ServerPlayer player = server.getPlayerList().getPlayer(this.playerUUID);
		if (player != null) {
			player.addItem(new ItemStack(Items.DIAMOND, this.amount));
		}
		return 100;
	}

	@Override
	public Codec<? extends Task> codec() {
		return CODEC;
	}
}
