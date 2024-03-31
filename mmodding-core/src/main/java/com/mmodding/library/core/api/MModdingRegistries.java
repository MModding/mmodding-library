package com.mmodding.library.core.api;

import com.mmodding.library.registry.api.LiteRegistry;
import com.mmodding.library.registry.api.companion.RegistryKeyAttachment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class MModdingRegistries {

	public static final LiteRegistry<Void> STELLAR_CYCLE = LiteRegistry.create();

	public static final LiteRegistry<Void> SQUARED_PORTAL = LiteRegistry.create();

	public static final LiteRegistry<Void> UNLINKED_SQUARED_PORTAL = LiteRegistry.create();

	public static final RegistryKeyAttachment<World, Boolean> DIFFERED_SEED = RegistryKeyAttachment.create(RegistryKeyAttachment.dynamic(RegistryKeys.WORLD));
}
