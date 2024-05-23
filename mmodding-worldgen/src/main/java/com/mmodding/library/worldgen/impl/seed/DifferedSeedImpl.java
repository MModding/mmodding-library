package com.mmodding.library.worldgen.impl.seed;

import com.mmodding.library.core.api.registry.extension.RegistryKeyAttachment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class DifferedSeedImpl {

	public static final RegistryKeyAttachment<World, Boolean> ATTACHMENT = RegistryKeyAttachment.create(RegistryKeyAttachment.dynamic(RegistryKeys.WORLD));
}
