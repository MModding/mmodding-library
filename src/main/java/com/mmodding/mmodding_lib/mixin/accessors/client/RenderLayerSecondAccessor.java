package com.mmodding.mmodding_lib.mixin.accessors.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderLayer.class)
public interface RenderLayerSecondAccessor {

    @Invoker("of")
    static RenderLayer.MultiPhase invokeOf(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
        throw new AssertionError();
    }
}
