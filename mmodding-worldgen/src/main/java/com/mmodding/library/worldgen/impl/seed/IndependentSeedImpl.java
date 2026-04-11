package com.mmodding.library.worldgen.impl.seed;

import com.mmodding.library.core.api.registry.attachment.DynamicResourceKeyAttachment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;

public class IndependentSeedImpl {

	public static final DynamicResourceKeyAttachment<Level, Boolean> ATTACHMENT = DynamicResourceKeyAttachment.create(Registries.DIMENSION);
}
