package com.mmodding.library.task.impl;

import com.mmodding.library.task.api.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

import java.util.Objects;

public class TaskHolder {

	public static final Codec<TaskHolder> CODEC = new TaskHolderCodec();

	private final Task task;
	private int delay;
	private int step; // unused if not multistep

	public TaskHolder(Task task, int delay) {
		this(task, delay, 0);
	}

	public TaskHolder(Task task, int delay, int step) {
		this.task = task;
		this.delay = delay;
		this.step = step;
	}

	// Returns true if "done".
	public boolean tick(MinecraftServer server) {
		if (this.delay > 0) this.delay--;
		if (this.delay == 0) {
			if (this.task instanceof ExecOnceTask eot) eot.execute(server);
			else if (this.task instanceof RepeatingTask rt) {
				this.delay = rt.execute(server);
			}
			else if (this.task instanceof MultiStepTask mst) {
				var redirector = mst.execute(server, this.step);
				if (redirector.step() != -1) {
					this.delay = redirector.delay();
					this.step = redirector.step();
				}
			}
		}
		return this.delay == 0;
	}

	public boolean isPersistent() {
		return this.task.codec() != null;
	}

	private static class TaskHolderCodec implements Codec<TaskHolder> {

		@Override
		public <T> DataResult<Pair<TaskHolder, T>> decode(DynamicOps<T> ops, T input) {
			DataResult<MapLike<T>> parsedMapResult = ops.getMap(input);
			if (parsedMapResult.isSuccess()) {
				MapLike<T> mapLike = parsedMapResult.getOrThrow();
				Identifier type = Identifier.parse(ops.getStringValue(mapLike.get("type")).getOrThrow());
				Codec<? extends Task> codec = PersistentTaskRegistryImpl.get(type);
				int delay = ops.getNumberValue(mapLike.get("delay")).getOrThrow().intValue();
				int step = ops.getNumberValue(Objects.requireNonNullElse(mapLike.get("step"), ops.createInt(0))).getOrThrow().intValue();
				return DataResult.success(Pair.of(new TaskHolder(codec.decode(ops, mapLike.get("context")).getOrThrow().getFirst(), delay, step), input));
			}
			else {
				throw new RuntimeException("Failed to decode task holder");
			}
		}

		@Override
		public <T> DataResult<T> encode(TaskHolder input, DynamicOps<T> ops, T prefix) {
			T map = ops.createMap(new Object2ObjectOpenHashMap<>());
			map = ops.set(map, "type", ops.createString(PersistentTaskRegistryImpl.getCodecId(input.task).toString()));
			map = ops.set(map, "delay", ops.createInt(input.delay));
			if (input.task instanceof MultiStepTask) {
				map = ops.set(map, "step", ops.createInt(input.step));
			}
			@SuppressWarnings("unchecked")
			Codec<Task> contextCodec = (Codec<Task>) input.task.codec();
			Objects.requireNonNull(contextCodec); // Should never happen as the internal task manager codec filters out holders with null codecs.
			map = ops.set(map, "context", contextCodec.encodeStart(ops, input.task).getOrThrow());
			return DataResult.success(map);
		}
	}
}
