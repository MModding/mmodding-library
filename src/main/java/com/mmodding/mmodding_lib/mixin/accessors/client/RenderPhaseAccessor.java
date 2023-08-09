package com.mmodding.mmodding_lib.mixin.accessors.client;

import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor {

    @Accessor("NO_TRANSPARENCY")
    static RenderPhase.Transparency getNoTransparency() {
        throw new AssertionError();
    }

    @Accessor("ADDITIVE_TRANSPARENCY")
    static RenderPhase.Transparency getAdditiveTransparency() {
        throw new AssertionError();
    }

    @Accessor("LIGHTNING_TRANSPARENCY")
    static RenderPhase.Transparency getLightningTransparency() {
        throw new AssertionError();
    }

    @Accessor("GLINT_TRANSPARENCY")
    static RenderPhase.Transparency getGlintTransparency() {
        throw new AssertionError();
    }

    @Accessor("CRUMBLING_TRANSPARENCY")
    static RenderPhase.Transparency getCrumblingTransparency() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_TRANSPARENCY")
    static RenderPhase.Transparency getTranslucentTransparency() {
        throw new AssertionError();
    }

    @Accessor("NO_SHADER")
    static RenderPhase.Shader getNoShader() {
        throw new AssertionError();
    }

    @Accessor("BLOCK_SHADER")
    static RenderPhase.Shader getBlockShader() {
        throw new AssertionError();
    }

    @Accessor("NEW_ENTITY_SHADER")
    static RenderPhase.Shader getNewEntityShader() {
        throw new AssertionError();
    }

    @Accessor("POSITION_COLOR_LIGHTMAP_SHADER")
    static RenderPhase.Shader getPositionColorLightMapShader() {
        throw new AssertionError();
    }

    @Accessor("POSITION_SHADER")
    static RenderPhase.Shader getPositionShader() {
        throw new AssertionError();
    }

    @Accessor("POSITION_COLOR_TEXTURE_SHADER")
    static RenderPhase.Shader getPositionColorTextureShader() {
        throw new AssertionError();
    }

    @Accessor("POSITION_TEXTURE_SHADER")
    static RenderPhase.Shader getPositionTextureShader() {
        throw new AssertionError();
    }

    @Accessor("POSITION_COLOR_TEXTURE_LIGHTMAP_SHADER")
    static RenderPhase.Shader getPositionColorTextureLightmapShader() {
        throw new AssertionError();
    }

    @Accessor("COLOR_SHADER")
    static RenderPhase.Shader getColorShader() {
        throw new AssertionError();
    }

    @Accessor("SOLID_SHADER")
    static RenderPhase.Shader getSolidShader() {
        throw new AssertionError();
    }

    @Accessor("CUTOUT_MIPPED_SHADER")
    static RenderPhase.Shader getCutoutMippedShader() {
        throw new AssertionError();
    }

    @Accessor("CUTOUT_SHADER")
    static RenderPhase.Shader getCutoutShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_SHADER")
    static RenderPhase.Shader getTranslucentShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_MOVING_BLOCK_SHADER")
    static RenderPhase.Shader getTranslucentMovingBlockShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_NO_CRUMBLING_SHADER")
    static RenderPhase.Shader getTranslucentNoCrumblingShader() {
        throw new AssertionError();
    }

    @Accessor("ARMOR_CUTOUT_NO_CULL_SHADER")
    static RenderPhase.Shader getArmorCutoutNoCullShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_SOLID_SHADER")
    static RenderPhase.Shader getEntitySolidShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_CUTOUT_SHADER")
    static RenderPhase.Shader getEntityCutoutShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_CUTOUT_NONULL_SHADER")
    static RenderPhase.Shader getEntityCutoutNonullShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_CUTOUT_NONULL_OFFSET_Z_SHADER")
    static RenderPhase.Shader getEntityCutoutNonullOffsetZShader() {
        throw new AssertionError();
    }

    @Accessor("ITEM_ENTITY_TRANSLUCENT_CULL_SHADER")
    static RenderPhase.Shader getItemEntityTranslucentCullShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_TRANSLUCENT_CULL_SHADER")
    static RenderPhase.Shader getEntityTranslucentCullShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_TRANSLUCENT_SHADER")
    static RenderPhase.Shader getEntityTranslucentShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_TRANSLUCENT_EMISSIVE_SHADER")
    static RenderPhase.Shader getEntityTranslucentEmissiveShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_SMOOTH_CUTOUT_SHADER")
    static RenderPhase.Shader getEntitySmoothCutoutShader() {
        throw new AssertionError();
    }

    @Accessor("BEACON_BEAM_SHADER")
    static RenderPhase.Shader getBeaconBeamShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_DECAL_SHADER")
    static RenderPhase.Shader getEntityDecalShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_NO_OUTLINE_SHADER")
    static RenderPhase.Shader getEntityNoOutlineShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_SHADOW_SHADER")
    static RenderPhase.Shader getEntityShadowShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_ALPHA_SHADER")
    static RenderPhase.Shader getEntityAlphaShader() {
        throw new AssertionError();
    }

    @Accessor("EYES_SHADER")
    static RenderPhase.Shader getEyesShader() {
        throw new AssertionError();
    }

    @Accessor("ENERGY_SWIRL_SHADER")
    static RenderPhase.Shader getEnergySwirlShader() {
        throw new AssertionError();
    }

    @Accessor("LEASH_SHADER")
    static RenderPhase.Shader getLeashShader() {
        throw new AssertionError();
    }

    @Accessor("WATER_MASK_SHADER")
    static RenderPhase.Shader getWaterMaskShader() {
        throw new AssertionError();
    }

    @Accessor("OUTLINE_SHADER")
    static RenderPhase.Shader getOutlineShader() {
        throw new AssertionError();
    }

    @Accessor("ARMOR_GLINT_SHADER")
    static RenderPhase.Shader getArmorGlintShader() {
        throw new AssertionError();
    }

    @Accessor("ARMOR_ENTITY_GLINT_SHADER")
    static RenderPhase.Shader getArmorEntityGlintShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_GLINT_SHADER")
    static RenderPhase.Shader getTranslucentGlintShader() {
        throw new AssertionError();
    }

    @Accessor("GLINT_SHADER")
    static RenderPhase.Shader getGlintShader() {
        throw new AssertionError();
    }

    @Accessor("DIRECT_GLINT_SHADER")
    static RenderPhase.Shader getDirectGlintShader() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_GLINT_SHADER")
    static RenderPhase.Shader getEntityGlintShader() {
        throw new AssertionError();
    }

    @Accessor("DIRECT_ENTITY_GLINT_SHADER")
    static RenderPhase.Shader getDirectEntityGlintShader() {
        throw new AssertionError();
    }

    @Accessor("CRUMBLING_SHADER")
    static RenderPhase.Shader getCrumblingShader() {
        throw new AssertionError();
    }

    @Accessor("TEXT_SHADER")
    static RenderPhase.Shader getTextShader() {
        throw new AssertionError();
    }

    @Accessor("TEXT_INTENSITY_SHADER")
    static RenderPhase.Shader getTextIntensityShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSPARENT_TEXT_SHADER")
    static RenderPhase.Shader getTransparentTextShader() {
        throw new AssertionError();
    }

    @Accessor("TRANSPARENT_TEXT_INTENSITY_SHADER")
    static RenderPhase.Shader getTransparentTextIntensityShader() {
        throw new AssertionError();
    }

    @Accessor("LIGHTNING_SHADER")
    static RenderPhase.Shader getLightningShader() {
        throw new AssertionError();
    }

    @Accessor("TRIPWIRE_SHADER")
    static RenderPhase.Shader getTripwireShader() {
        throw new AssertionError();
    }

    @Accessor("END_PORTAL_SHADER")
    static RenderPhase.Shader getEndPortalShader() {
        throw new AssertionError();
    }

    @Accessor("END_GATEWAY_SHADER")
    static RenderPhase.Shader getEndGatewayShader() {
        throw new AssertionError();
    }

    @Accessor("LINES_SHADER")
    static RenderPhase.Shader getLinesShader() {
        throw new AssertionError();
    }

    @Accessor("MIPMAP_BLOCK_ATLAS_TEXTURE")
    static RenderPhase.Texture getMipmapBlockAtlasTexture() {
        throw new AssertionError();
    }

    @Accessor("BLOCK_ATLAS_TEXTURE")
    static RenderPhase.Texture getBlockAtlasTexture() {
        throw new AssertionError();
    }

    @Accessor("NO_TEXTURE")
    static RenderPhase.TextureBase getNoTexture() {
        throw new AssertionError();
    }

    @Accessor("DEFAULT_TEXTURING")
    static RenderPhase.Texturing getDefaultTexturing() {
        throw new AssertionError();
    }


    @Accessor("GLINT_TEXTURING")
    static RenderPhase.Texturing getGlintTexturing() {
        throw new AssertionError();
    }

    @Accessor("ENTITY_GLINT_TEXTURING")
    static RenderPhase.Texturing getEntityGlintTexturing() {
        throw new AssertionError();
    }

    @Accessor("ENABLE_LIGHTMAP")
    static RenderPhase.Lightmap getEnableLightmap() {
        throw new AssertionError();
    }

    @Accessor("DISABLE_LIGHTMAP")
    static RenderPhase.Lightmap getDisableLightmap() {
        throw new AssertionError();
    }

    @Accessor("ENABLE_OVERLAY_COLOR")
    static RenderPhase.Overlay getEnableOverlayColor() {
        throw new AssertionError();
    }

    @Accessor("DISABLE_OVERLAY_COLOR")
    static RenderPhase.Overlay getDisableOverlayColor() {
        throw new AssertionError();
    }

    @Accessor("ENABLE_CULLING")
    static RenderPhase.Cull getEnableCulling() {
        throw new AssertionError();
    }

    @Accessor("DISABLE_CULLING")
    static RenderPhase.Cull getDisableCulling() {
        throw new AssertionError();
    }

    @Accessor("ALWAYS_DEPTH_TEST")
    static RenderPhase.DepthTest getAlwaysDepthTest() {
        throw new AssertionError();
    }

    @Accessor("EQUAL_DEPTH_TEST")
    static RenderPhase.DepthTest getEqualDepthTest() {
        throw new AssertionError();
    }

    @Accessor("LEQUAL_DEPTH_TEST")
    static RenderPhase.DepthTest getLequalDepthTest() {
        throw new AssertionError();
    }

    @Accessor("ALL_MASK")
    static RenderPhase.WriteMaskState getAllMask() {
        throw new AssertionError();
    }

    @Accessor("COLOR_MASK")
    static RenderPhase.WriteMaskState getColorMask() {
        throw new AssertionError();
    }

    @Accessor("DEPTH_MASK")
    static RenderPhase.WriteMaskState getDepthMask() {
        throw new AssertionError();
    }

    @Accessor("NO_LAYERING")
    static RenderPhase.Layering getNoLayering() {
        throw new AssertionError();
    }


    @Accessor("POLYGON_OFFSET_LAYERING")
    static RenderPhase.Layering getPolygonOffsetLayering() {
        throw new AssertionError();
    }

    @Accessor("VIEW_OFFSET_Z_LAYERING")
    static RenderPhase.Layering getViewOffsetZLayering() {
        throw new AssertionError();
    }

    @Accessor("MAIN_TARGET")
    static RenderPhase.Target getMainTarget() {
        throw new AssertionError();
    }

    @Accessor("OUTLINE_TARGET")
    static RenderPhase.Target getOutlineTarget() {
        throw new AssertionError();
    }

    @Accessor("TRANSLUCENT_TARGET")
    static RenderPhase.Target getTranslucentTarget() {
        throw new AssertionError();
    }

    @Accessor("PARTICLES_TARGET")
    static RenderPhase.Target getParticlesTarget() {
        throw new AssertionError();
    }

    @Accessor("WEATHER_TARGET")
    static RenderPhase.Target getWeatherTarget() {
        throw new AssertionError();
    }

    @Accessor("CLOUDS_TARGET")
    static RenderPhase.Target getCloudsTarget() {
        throw new AssertionError();
    }

    @Accessor("ITEM_TARGET")
    static RenderPhase.Target getItemTarget() {
        throw new AssertionError();
    }

    @Accessor("FULL_LINE_WIDTH")
    static RenderPhase.LineWidth getFullLineWidth() {
        throw new AssertionError();
    }
}
