package com.mmodding.mmodding_lib;

import com.google.common.collect.ImmutableList;
import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.library.utils.StellarUtils;
import com.mmodding.mmodding_lib.states.persistant.StellarStatuses;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Locale;

public class MModdingCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			CommandManager.literal("mmodding")
				.then(
					CommandManager.literal("discard")
						.requires(source -> source.hasPermissionLevel(2))
						.executes(context -> MModdingCommand.discard(context.getSource(), ImmutableList.of(context.getSource().getEntityOrThrow())))
						.then(
							CommandManager.argument("targets", EntityArgumentType.entities())
								.executes(context -> MModdingCommand.discard(context.getSource(), EntityArgumentType.getEntities(context, "targets")))
						)
				)
				.then(
					CommandManager.literal("soundtrack")
						.then(
							CommandManager.literal("release")
								.executes(context -> MModdingCommand.soundtrack(context.getSource(), SoundtrackOperation.RELEASE))
						)
						.then(
							CommandManager.literal("clear")
								.executes(context -> MModdingCommand.soundtrack(context.getSource(), SoundtrackOperation.CLEAR))
						)
						.then(
							CommandManager.literal("stop")
								.executes(context -> MModdingCommand.soundtrack(context.getSource(), SoundtrackOperation.STOP))
						)
				)
				.then(
					CommandManager.literal("stellar")
						.requires(source -> source.hasPermissionLevel(2))
						.then(
							CommandManager.argument("status", IdentifierArgumentType.identifier())
								.suggests(
									(context, builder) -> {
										StellarStatuses stellarStatuses = ((ServerWorldDuckInterface) context.getSource().getWorld())
											.mmodding_lib$getStellarStatuses();
										return CommandSource.suggestIdentifiers(stellarStatuses.getMap().keySet(), builder);
									}
								)
								.then(
									CommandManager.literal("set")
										.then(
											CommandManager.argument("time", TimeArgumentType.time())
												.executes(
													context -> MModdingCommand.stellar(
														context.getSource(),
														context.getArgument("status", Identifier.class),
														StellarOperation.SET,
														IntegerArgumentType.getInteger(context, "time")
													)
												)
										)
								)
								.then(
									CommandManager.literal("add")
										.then(
											CommandManager.argument("time", TimeArgumentType.time())
												.executes(
													context -> MModdingCommand.stellar(
														context.getSource(),
														context.getArgument("status", Identifier.class),
														StellarOperation.ADD,
														IntegerArgumentType.getInteger(context, "time")
													)
												)
										)
								)
								.then(
									CommandManager.literal("query")
										.then(
											CommandManager.literal("time")
												.executes(
													context -> MModdingCommand.stellar(
														context.getSource(),
														context.getArgument("status", Identifier.class),
														StellarOperation.QUERY,
														0
													)
												)
										)
										.then(
											CommandManager.literal("total")
												.executes(
													context -> MModdingCommand.stellar(
														context.getSource(),
														context.getArgument("status", Identifier.class),
														StellarOperation.QUERY,
														1
													)
												)
										)
										.then(
											CommandManager.literal("rotation")
												.executes(
													context -> MModdingCommand.stellar(
														context.getSource(),
														context.getArgument("status", Identifier.class),
														StellarOperation.QUERY,
														2
													)
												)
										)
								)
						)
				)
		);
	}

	private static int discard(ServerCommandSource source, Collection<? extends Entity> targets) {
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

	private static int soundtrack(ServerCommandSource source, SoundtrackOperation operation) throws CommandSyntaxException {
		if (source.getEntity() == null) {
			source.sendError(Text.translatable("commands.mmodding.soundtrack.not_an_entity"));
		}
		return switch (operation) {
			case RELEASE -> {
				source.getPlayer().getSoundtrackPlayer().release();
				source.sendFeedback(Text.translatable("commands.mmodding.soundtrack.released"), false);
				yield 0;
			}
			case CLEAR -> {
				source.getPlayer().getSoundtrackPlayer().clear();
				source.sendFeedback(Text.translatable("commands.mmodding.soundtrack.cleared"), false);
				yield 1;
			}
			case STOP -> {
				source.getPlayer().getSoundtrackPlayer().stop();
				source.sendFeedback(Text.translatable("commands.mmodding.soundtrack.stopped"), false);
				yield 2;
			}
		};
	}

	private static int stellar(ServerCommandSource source, Identifier status, StellarOperation operation, int value) {
		StellarStatuses stellarStatuses = ((ServerWorldDuckInterface) source.getWorld()).mmodding_lib$getStellarStatuses();
		StellarStatus stellarStatus = StellarUtils.getStatusInWorld(source.getWorld(), status);

		return switch (operation) {
			case SET -> {
				long time = value > stellarStatus.getFullTime() ? value % stellarStatus.getFullTime() : value;
				stellarStatuses.getMap().put(status, StellarStatus.of(time, stellarStatus.getFullTime()));
				source.sendFeedback(Text.translatable("commands.mmodding.stellar.set", status.toString(), time), true);
				yield (int) time;
			}
			case ADD -> {
				long current = stellarStatus.getCurrentTime() + value;
				long time = current > stellarStatus.getFullTime() ? current % stellarStatus.getFullTime() : current;
				stellarStatuses.getMap().put(status, StellarStatus.of(time, stellarStatus.getFullTime()));
				source.sendFeedback(Text.translatable("commands.mmodding.stellar.add", current, status.toString(), time), true);
				yield (int) time;
			}
			case QUERY -> switch (value) {
				case 0 -> {
					long current = stellarStatus.getCurrentTime();
					source.sendFeedback(Text.translatable("commands.mmodding.stellar.query.time", status.toString(), current), true);
					yield (int) current;
				}
				case 1 -> {
					long full = stellarStatus.getFullTime();
					source.sendFeedback(Text.translatable("commands.mmodding.stellar.query.total", status.toString(), full), true);
					yield (int) full;
				}
				case 2 -> {
					long numerator = stellarStatus.getCurrentTime();
					long denominator = stellarStatus.getFullTime();
					float ratio = (float) numerator / (float) denominator * 100.0f;
					source.sendFeedback(Text.translatable("commands.mmodding.stellar.query.rotation", status.toString(), String.format(Locale.ROOT, "%f", ratio), numerator, denominator), true);
					yield (int) ratio;
				}
				default -> 3;
			};
		};
	}

	private enum SoundtrackOperation {
		RELEASE,
		CLEAR,
		STOP
	}

	private enum StellarOperation {
		SET,
		ADD,
		QUERY
	}
}
