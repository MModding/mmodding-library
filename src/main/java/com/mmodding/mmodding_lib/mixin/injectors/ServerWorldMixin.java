package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends WorldMixin implements WorldUtils.TickTaskServer {

	@Shadow
	@NotNull
	public abstract MinecraftServer getServer();

	@Unique
	private final List<MutablePair<Long, Runnable>> tasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<Long, Runnable>> repeatingTasks = new ArrayList<>();

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {

		for (int i = 0; i < this.tasks.size(); i++) {
			MutablePair<Long, Runnable> task = this.tasks.get(i);

			task.setLeft(task.getLeft() - 1);
			if (task.getLeft() <= 0L) {
				task.getRight().run();

				this.tasks.remove(i);
				i--;
			}
		}

		for (int i = 0; i < this.repeatingTasks.size(); i++) {
			MutablePair<Long, Runnable> task = this.repeatingTasks.get(i);

			task.setLeft(task.getLeft() - 1);
			task.getRight().run();

			if (task.getLeft() <= 0L) {
				this.repeatingTasks.remove(i);
				i--;
			}
		}
	}

	@Override
	public void doTaskAfter(long ticksToWait, Runnable run) {
		this.tasks.add(new MutablePair<>(ticksToWait, run));
	}

	@Override
	public void repeatTaskUntil(long ticksUntil, Runnable run) {
		this.repeatingTasks.add(new MutablePair<>(ticksUntil, run));
	}

	@Inject(method = "getSeed", at = @At("HEAD"), cancellable = true)
	private void getDifferedSeed(CallbackInfoReturnable<Long> cir) {
		MModdingGlobalMaps.getDifferedDimensionSeeds().forEach(worldKey -> {
			ServerWorld thisWorld = (ServerWorld) (Object) this;
			if (thisWorld.getRegistryKey().equals(worldKey)) {
				GeneratorOptionsDuckInterface ducked = (GeneratorOptionsDuckInterface) this.getServer().getSaveProperties().getGeneratorOptions();
				if (!ducked.containsDimensionSeedAddend(worldKey)) {
					ducked.addDimensionSeedAddend(worldKey, this.random.nextInt(100000) + 1);
				}
				long normalSeed = this.getServer().getSaveProperties().getGeneratorOptions().getSeed();
				long differedSeed = Long.sum(normalSeed, ducked.getDimensionSeedAddend(worldKey));
				cir.setReturnValue(differedSeed);
			}
		});
	}
}
