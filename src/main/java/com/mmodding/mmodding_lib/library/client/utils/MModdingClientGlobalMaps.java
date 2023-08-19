package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.library.stellar.StellarObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ClientOnly
public class MModdingClientGlobalMaps {

    static final List<StellarObject> STELLAR_OBJECTS = new ArrayList<>();

    static final Map<Identifier, GlintPack> GLINT_PACKS = new HashMap<>();

	static final Map<Item, Pair<GlintPackView, Predicate<Item>>> GLINT_PACK_OVERRIDES = new HashMap<>();

    public static Stream<StellarObject> getStellarObjects() {
        return STELLAR_OBJECTS.stream();
    }

    public static boolean hasGlintPack(Identifier identifier) {
        return GLINT_PACKS.containsKey(identifier);
    }

    public static GlintPack getGlintPack(Identifier identifier) {
        return GLINT_PACKS.get(identifier);
    }

	public static Set<Item> getGlintPackOverrideKeys() {
        return GLINT_PACK_OVERRIDES.keySet();
    }

	public static Pair<GlintPackView, Predicate<Item>> getGlintPackOverride(Item item) {
		return GLINT_PACK_OVERRIDES.get(item);
	}
}
