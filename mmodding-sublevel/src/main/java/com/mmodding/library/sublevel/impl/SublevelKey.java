package com.mmodding.library.sublevel.impl;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class SublevelKey extends ResourceKey<Level> {

	private SublevelKey(Identifier registryName, Identifier identifier) {
		super(registryName, identifier);
	}

	/**
	 * Since sublevels are not recreated for a server instance, and that we want the sublevels to have keys considered
	 * different (to prevent most wrong behaviors), we just recreate a new object for each sublevel.
	 * So `==` on sublevel keys should only return true if that's the same key object reference, so if it's from the
	 * same sublevel.
	 * The idea of comparing level keys with the plain object references is already used for normal levels;
	 * it's nothing new. It's even better: it ensures that it will work for most of the things.
 	 */
	public static SublevelKey of(ResourceKey<Level> type) {
		return new SublevelKey(type.registry(), type.identifier());
	}
}
