package com.mmodding.mmodding_lib.glint;

import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import com.mmodding.mmodding_lib.library.client.utils.MModdingClientGlobalMaps;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public record GlintPackView(Identifier identifier) {

    @ClientOnly
    public GlintPack getGlintPack() {
        if (MModdingClientGlobalMaps.hasGlintPack(this.identifier)) {
            return MModdingClientGlobalMaps.getGlintPack(this.identifier);
        }
        else {
            throw new IllegalArgumentException("Glint Pack With Identifier " + this.identifier + "Does Not Exist");
        }
    }
}
