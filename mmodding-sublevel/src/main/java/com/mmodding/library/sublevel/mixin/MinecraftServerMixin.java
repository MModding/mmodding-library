package com.mmodding.library.sublevel.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.sublevel.api.SublevelType;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import com.mmodding.library.sublevel.impl.SublevelTypeImpl;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.lang3.stream.Streams;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

	@Shadow
	@Final
	private static Logger LOGGER;

	@Shadow
	public Map<ResourceKey<Level>, ServerLevel> levels;

	@Shadow
	@Final
	public LevelStorageSource.LevelStorageAccess storageSource;

	@Shadow
	public abstract Iterable<ServerLevel> getAllLevels();

	@Shadow
	@Final
	private LayeredRegistryAccess<RegistryLayer> registries;

	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "createLevels", at = @At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;"))
	private <E> Iterator<E> preventSubLevelRoots(Iterator<E> original) {
		Predicate<E> rootDisabled = element -> {
			Map.Entry<ResourceKey<LevelStem>, LevelStem> entry = (Map.Entry<ResourceKey<LevelStem>, LevelStem>) element;
			assert entry != null;
			return !SublevelTypeImpl.ROOT_LEVEL_DISABLED.contains(entry.getKey().identifier());
		};
		return Iterators.filter(original, rootDisabled::test);
	}

	@Inject(method = "createLevels", at = @At("TAIL"))
	private void loadSublevels(CallbackInfo ci) {
		for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : this.registries.compositeAccess().lookupOrThrow(Registries.LEVEL_STEM).entrySet()) {
			if (SublevelTypeImpl.TYPES.containsKey(entry.getKey().identifier())) {
				SublevelTypeImpl<?> subLevelType = SublevelTypeImpl.TYPES.get(entry.getKey().identifier());
				Path subRoot = this.storageSource.getDimensionPath(subLevelType.dimension());
				if (subRoot.toFile().exists()) {
					try {
						Files.list(subRoot).forEach(subPath -> subLevelType.getOrCreate(this.levels.get(Level.OVERWORLD), subPath.toFile().getName()));
					} catch (IOException error) {
						throw new RuntimeException(error);
					}
				}
			}
		}
	}

	@WrapMethod(method = "getAllLevels")
	private Iterable<ServerLevel> addSublevels(Operation<Iterable<ServerLevel>> original) {
		Collection<ServerLevel> result = new ArrayList<>((Collection<ServerLevel>) original.call());
		for (SublevelTypeImpl<?> type : SublevelTypeImpl.TYPES.values()) {
			result.addAll(type.levelValues());
		}
		return result;
	}

	// We silence them individually to log them by types
	@WrapOperation(method = "saveAllChunks", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
	private void silenceIndividualSublevels(Logger instance, String s, Object o0, Object o1, Operation<Void> original) {
		Level level = (Level) o0;
		if (!(level instanceof ServerSublevel<?>)) {
			original.call(instance, s, o0, o1);
		}
	}

	@SuppressWarnings("unchecked")
	@Inject(method = "saveAllChunks", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getSingleplayerProfile()Lcom/mojang/authlib/GameProfile;"))
	private void logSublevelsByTypes(boolean silent, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
		if (!silent) {
			List<SublevelType<?>> collectedTypes = (List<SublevelType<?>>) (List<?>) Streams.of(this.getAllLevels()).filter(level -> level instanceof ServerSublevel<?>)
				.map(level -> ((ServerSublevel<?>) level).getType())
				.toList();
			Map<ResourceKey<Level>, Integer> weightedTypes = new Object2ObjectOpenHashMap<>();
			for (SublevelType<?> type : collectedTypes) {
				weightedTypes.put(type.dimension(), weightedTypes.computeIfAbsent(type.dimension(), _ -> 0) + 1);
			}
			weightedTypes.forEach((key, amount) -> LOGGER.info("Saving chunks for {} sublevels of type {}", amount, key));
		}
	}

	// Sublevels are associated to a server. If one is created and then saved, but then reused on another server,
	// it will lead to unexpected behaviors and crashes.
	@Inject(method = "stopServer", at = @At(value = "TAIL"))
	private void clearSubLevelsOnStop(CallbackInfo ci) {
		SublevelTypeImpl.TYPES.values().forEach(SublevelTypeImpl::clearLevels);
	}
}
