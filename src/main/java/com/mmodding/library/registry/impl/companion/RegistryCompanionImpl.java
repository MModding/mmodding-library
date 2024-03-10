package com.mmodding.library.registry.impl.companion;

import com.mmodding.library.registry.api.LiteRegistry;
import com.mmodding.library.registry.api.companion.RegistryCompanion;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class RegistryCompanionImpl<T, E> implements RegistryCompanion<T, E> {

	private final RegistryEntryAttachment<T, String> rea;

	private final Map<String, LiteRegistry<E>> map;

	public RegistryCompanionImpl(Registry<T> registry, Identifier identifier) {
		this.rea = RegistryEntryAttachment.stringBuilder(registry, identifier)
			.defaultValue("empty")
			.build();
		this.map = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public void addCompanion(T element) {
		this.addCompanion(element, map -> {});
	}

	@Override
	public void addCompanion(T element, Consumer<LiteRegistry<E>> action) {
		String uuid = UUID.randomUUID().toString();
		this.rea.put(element, uuid);
		LiteRegistry<E> registry = LiteRegistry.create();
		action.accept(registry);
		this.map.put(uuid, registry);
	}

	@Override
	public LiteRegistry<E> getCompanion(T element) {
		return this.map.get(this.rea.getNullable(element));
	}
}
