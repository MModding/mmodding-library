package com.mmodding.mmodding_lib;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;

public class MModdingCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			CommandManager.literal("mmodding")
				.requires(source -> source.hasPermissionLevel(2))
				.then(
					CommandManager.literal("discard")
						.executes(context -> MModdingCommand.forceKill(context.getSource(), ImmutableList.of(context.getSource().getEntityOrThrow())))
						.then(
							CommandManager.argument("targets", EntityArgumentType.entities())
								.executes(context -> MModdingCommand.forceKill(context.getSource(), EntityArgumentType.getEntities(context, "targets")))
						)
				)
		);
	}

	private static int forceKill(ServerCommandSource source, Collection<? extends Entity> targets) {
		for (Entity entity : targets) {
			entity.discard();
		}

		if (targets.size() == 1) {
			source.sendFeedback(Text.translatable("commands.mmodding.discard.success.single", targets.iterator().next().getDisplayName()), true);
		} else {
			source.sendFeedback(Text.translatable("commands.mmodding.discard.success.multiple", targets.size()), true);
		}

		return targets.size();
	}
}
