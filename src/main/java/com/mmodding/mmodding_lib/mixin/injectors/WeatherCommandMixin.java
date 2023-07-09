package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.utils.TextUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WeatherCommand;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherCommand.class)
public class WeatherCommandMixin {

	private static void redirectingMessage(ServerCommandSource source, String weatherStatus, int duration, CallbackInfoReturnable<Integer> cir) {
		if (source.getWorld().getRegistryKey() != World.OVERWORLD) {
			String baseRedirectedCommand = "/execute in minecraft:overworld run weather " + weatherStatus;
			String finalRedirectedCommand = duration != 6000 && duration != 0 ? baseRedirectedCommand + " " + duration / 20 : baseRedirectedCommand;

			MutableText mmoddingLibrary = Texts.bracketed(Text.literal("MModding Library")).styled(style -> style.withColor(Formatting.DARK_PURPLE));
			MutableText notInOverworld = Text.translatable("mmodding_lib.commands.redirections.weather").styled(style -> style.withColor(Formatting.BLUE));
			MutableText fixedCommand = Text.translatable("mmodding_lib.commands.redirections.weather.fixed").styled(style -> style
				.withColor(Formatting.AQUA)
				.withUnderline(true)
				.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, finalRedirectedCommand))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("mmodding_lib.commands.redirections.weather.hover", weatherStatus)))
			);

			source.sendFeedback(TextUtils.spaceBetween(mmoddingLibrary, notInOverworld, fixedCommand), false);
			cir.setReturnValue(duration);
		}
	}

	@Inject(method = "executeClear", at = @At("HEAD"), cancellable = true)
	private static void executeClear(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
		WeatherCommandMixin.redirectingMessage(source, "clear", duration, cir);
	}

	@Inject(method = "executeRain", at = @At("HEAD"), cancellable = true)
	private static void executeRain(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
		WeatherCommandMixin.redirectingMessage(source, "rain", duration, cir);
	}

	@Inject(method = "executeThunder", at = @At("HEAD"), cancellable = true)
	private static void executeThunder(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
		WeatherCommandMixin.redirectingMessage(source, "thunder", duration, cir);
	}
}
