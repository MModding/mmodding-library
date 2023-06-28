package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MModdingGlobalMaps {

	static final Map<Identifier, Pair<? extends Block, ? extends CustomSquaredPortalBlock>> customSquaredPortals = new HashMap<>();

	public static Set<Identifier> getCustomSquaredPortalKeys() {
		return customSquaredPortals.keySet();
	}

	public static Pair<? extends Block, ? extends CustomSquaredPortalBlock> getCustomSquaredPortal(Identifier identifier) {
		return customSquaredPortals.getOrDefault(identifier, null);
	}
}
