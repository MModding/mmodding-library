package com.mmodding.library.rendering.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class TextureAliasesImpl {

	private static final Map<Identifier, Identifier> ALIASES = new Object2ObjectOpenHashMap<>();

	public static Identifier applyAliases(Identifier source) {
		return ALIASES.getOrDefault(source, source);
	}

	public static void create(Identifier alias, Identifier target) {
		ALIASES.put(alias, target);
	}
}
