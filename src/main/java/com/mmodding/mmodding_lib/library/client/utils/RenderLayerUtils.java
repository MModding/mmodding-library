package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import com.mmodding.mmodding_lib.mixin.accessors.client.BufferBuilderStorageAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.client.RenderLayerFirstAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.client.RenderLayerSecondAccessor;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class RenderLayerUtils {

    public static void addGlintPack(Identifier identifier, GlintPack glintPack) {
        MModdingClientGlobalMaps.GLINT_PACKS.put(identifier, glintPack);
    }

    public static void addEntityBuilder(RenderLayer layer) {
        ((BufferBuilderStorageAccessor) MinecraftClient.getInstance().getBufferBuilders()).getEntityBuilders().put(layer, new BufferBuilder(layer.getExpectedBufferSize()));
    }

    public static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, RenderLayer.MultiPhaseParameters phaseData) {
        return RenderLayerFirstAccessor.of(name, vertexFormat, drawMode, expectedBufferSize, phaseData);
    }

    public static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
        return RenderLayerSecondAccessor.of(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }
}
