package com.mmodding.library.worldgen.impl.seed;

import com.mmodding.library.core.api.registry.attachment.DynamicRegistryKeyAttachment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class IndependentSeedImpl {

	public static final DynamicRegistryKeyAttachment<World, Boolean> ATTACHMENT = DynamicRegistryKeyAttachment.create(RegistryKeys.WORLD);
}
