package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.ducks.ClientStellarStatusDuckInterface;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.library.stellar.client.ClientStellarStatus;
import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import com.mmodding.mmodding_lib.mixin.injectors.WorldMixin;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends WorldMixin implements ClientStellarStatusDuckInterface, WorldUtils.TickTaskClient {

	@Unique
	private final Map<Identifier, ClientStellarStatus> allClientStellarStatus = new HashMap<>();

	@Unique
	private final List<MutablePair<Long, Runnable>> tasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<Long, Runnable>> repeatingTasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<MutableTriple<Integer, Long, Runnable>, Integer>> eachTasks = new ArrayList<>();

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(ClientPlayNetworkHandler netHandler, ClientWorld.Properties clientWorldProperties, RegistryKey<World> registryKey, Holder<DimensionType> dimensionType, int chunkManager, int simulationDistance, Supplier<Profiler> profiler, WorldRenderer worldRenderer, boolean debugWorld, long seed, CallbackInfo ci) {
		MModdingGlobalMaps.getStellarCycleKeys().forEach((identifier) -> {
			StellarCycle stellarCycle = MModdingGlobalMaps.getStellarCycle(identifier);
			if (stellarCycle.getWorldKey().equals(this.getRegistryKey())) {
				this.allClientStellarStatus.putIfAbsent(identifier, ClientStellarStatus.of(stellarCycle));
			}
		});
	}

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

	@Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;setTimeOfDay(J)V"))
	private void tickTime(CallbackInfo ci) {
		this.allClientStellarStatus.forEach((key, value) -> value.tick());
	}

	@Override
	public void mmodding_lib$setStellarStatus(Identifier identifier, ClientStellarStatus stellarStatus) {
		this.allClientStellarStatus.put(identifier, stellarStatus);
	}

	@Override
	public StellarStatus mmodding_lib$getStellarStatus(Identifier identifier) {
		return this.allClientStellarStatus.get(identifier);
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
}
