package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import com.mmodding.mmodding_lib.states.persistant.StellarStatuses;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
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
public abstract class ServerWorldMixin extends WorldMixin implements WorldUtils.TickTaskServer, ServerWorldDuckInterface {

	@Unique
	private final List<MutablePair<Long, Runnable>> tasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<Long, Runnable>> repeatingTasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<MutableTriple<Integer, Long, Runnable>, Integer>> eachTasks = new ArrayList<>();

	@Unique
	protected StellarStatuses stellarStatuses;

	@Shadow
	@NotNull
	public abstract MinecraftServer getServer();

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

		for (int i = 0 ; i < this.eachTasks.size(); i++) {
			MutablePair<MutableTriple<Integer, Long, Runnable>, Integer> task = this.eachTasks.get(i);

			MutableTriple<Integer, Long, Runnable> params = task.getLeft();

			params.setMiddle(params.getMiddle() - 1);
			task.setRight(task.getRight() + 1);

			if (task.getRight() >= params.getLeft()) {
				params.getRight().run();
				task.setRight(0);
			}

			if (params.getMiddle() <= 0L) {
				this.eachTasks.remove(i);
				i--;
			}
		}
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tickTime()V"))
	private void tick(CallbackInfo ci) {
		this.stellarStatuses.tick(this.getServer());
	}

	@Inject(method = "getSeed", at = @At("HEAD"), cancellable = true)
	private void getSeed(CallbackInfoReturnable<Long> cir) {
		MModdingGlobalMaps.getDifferedDimensionSeeds().forEach(worldKey -> {
			ServerWorld thisWorld = (ServerWorld) (Object) this;
			if (thisWorld.getRegistryKey().equals(worldKey)) {
				GeneratorOptionsDuckInterface ducked = (GeneratorOptionsDuckInterface) this.getServer().getSaveProperties().getGeneratorOptions();
				if (!ducked.mmodding_lib$containsDimensionSeedAddend(worldKey)) {
					ducked.mmodding_lib$addDimensionSeedAddend(worldKey, this.random.nextInt(100000) + 1);
				}
				long normalSeed = this.getServer().getSaveProperties().getGeneratorOptions().getSeed();
				long differedSeed = Long.sum(normalSeed, ducked.mmodding_lib$getDimensionSeedAddend(worldKey));
				cir.setReturnValue(differedSeed);
			}
		});
	}

	@Override
	public void mmodding_lib$doTaskAfter(long ticksToWait, Runnable run) {
		this.tasks.add(new MutablePair<>(ticksToWait, run));
	}

	@Override
	public void mmodding_lib$repeatTaskUntil(long ticksUntil, Runnable run) {
		this.repeatingTasks.add(new MutablePair<>(ticksUntil, run));
	}

	@Override
	public void mmodding_lib$repeatTaskEachTimeUntil(int ticksBetween, long ticksUntil, Runnable run) {
		this.eachTasks.add(new MutablePair<>(new MutableTriple<>(ticksBetween, ticksUntil, run), 0));
	}

	@Override
	public StellarStatuses mmodding_lib$createStellarStatuses() {
		return this.stellarStatuses = this.fillStellarStatuses(new StellarStatuses());
	}

	@Override
	public StellarStatuses mmodding_lib$stellarStatusesFromNbt(NbtCompound nbt) {
		return this.stellarStatuses = (StellarStatuses) this.mmodding_lib$createStellarStatuses().readNbt(nbt);
	}

	@Override
	public StellarStatuses mmodding_lib$getStellarStatuses() {
		return this.stellarStatuses;
	}
}
