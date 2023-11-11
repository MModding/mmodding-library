package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.library.stellar.client.StellarObject;
import com.mmodding.mmodding_lib.mixin.accessors.client.BufferBuilderStorageAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.client.RenderLayerFirstAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.client.RenderLayerSecondAccessor;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.function.Predicate;

@ClientOnly
public class RenderLayerUtils {

    public static void addStellarObject(StellarObject stellarObject) {
        MModdingClientGlobalMaps.STELLAR_OBJECTS.add(stellarObject);
    }

    public static void addGlintPack(Identifier identifier, GlintPack glintPack) {
        MModdingClientGlobalMaps.GLINT_PACKS.put(identifier, glintPack);
    }

    public static void addGlintPackOverride(Item target, GlintPackView view) {
        RenderLayerUtils.addGlintPackOverride(target, view, (item) -> true);
    }

	public static void addGlintPackOverride(Item target, GlintPackView view, Predicate<Item> canApply) {
		MModdingClientGlobalMaps.GLINT_PACK_OVERRIDES.put(target, new Pair<>(view, canApply));
	}

    public static void addEntityBuilder(RenderLayer layer) {
        ((BufferBuilderStorageAccessor) MinecraftClient.getInstance().getBufferBuilders()).getEntityBuilders().put(layer, new BufferBuilder(layer.getExpectedBufferSize()));
    }

    public static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, RenderLayer.MultiPhaseParameters phaseData) {
        return RenderLayerFirstAccessor.invokeOf(name, vertexFormat, drawMode, expectedBufferSize, phaseData);
    }

    public static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
        return RenderLayerSecondAccessor.invokeOf(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }
}
