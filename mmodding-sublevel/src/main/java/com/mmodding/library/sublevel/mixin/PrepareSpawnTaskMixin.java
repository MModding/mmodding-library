package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.sublevel.impl.SublevelTypeImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.config.PrepareSpawnTask;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(PrepareSpawnTask.class)
public class PrepareSpawnTaskMixin {

	@Shadow
	@Final
	private MinecraftServer server;

	// try retrieving a sublevel if the detected level is null, so that the entity does not get back to overworld on login
	@SuppressWarnings({"unchecked", "OptionalUsedAsFieldOrParameterType"})
	@ModifyExpressionValue(method = "start", at = @At(value = "INVOKE", target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;", ordinal = 1))
	private <U> Optional<U> checkForSubLevels(Optional<U> original, @Local(name = "loadedPosition") ServerPlayer.SavedPosition loadedPosition, @Local(name = "loadedData") Optional<ValueInput> loadedData) {
		Optional<Level> casted = (Optional<Level>) original;
		return (Optional<U>) casted.or(() -> {
			if (loadedPosition.dimension().isPresent() && loadedData.isPresent()) {
				Optional<String> result = loadedData.get().getString("mmodding:sublevel");
				if (result.isPresent()) {
					SublevelTypeImpl<?> subLevelType = SublevelTypeImpl.TYPES.get(loadedPosition.dimension().get().identifier());
					return Optional.of(subLevelType.getOrCreate(this.server.getLevel(Level.OVERWORLD), result.get()));
				}
			}
			return Optional.empty();
		});
	}
}
