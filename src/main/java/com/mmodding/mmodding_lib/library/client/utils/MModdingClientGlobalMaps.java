package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public class MModdingClientGlobalMaps {

    protected static final Map<Identifier, GlintPack> GLINT_PACKS = new HashMap<>();

    public static boolean hasGlintPack(Identifier identifier) {
        return GLINT_PACKS.containsKey(identifier);
    }

    public static GlintPack getGlintPack(Identifier identifier) {
        return GLINT_PACKS.get(identifier);
    }
}
