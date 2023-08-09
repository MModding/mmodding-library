package com.mmodding.mmodding_lib.library.client.render;

import com.mmodding.mmodding_lib.mixin.accessors.client.RenderPhaseAccessor;
import net.minecraft.client.render.RenderPhase;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class RenderLayerElements {

	public static final Transparencies TRANSPARENCY = new Transparencies();
	public static final Shader SHADER = new Shader();
	public static final Texture TEXTURE = new Texture();
	public static final TextureBase TEXTURE_BASE = new TextureBase();
	public static final Texturing TEXTURING = new Texturing();
	public static final Lightmap LIGHTMAP = new Lightmap();
	public static final Overlay OVERLAY = new Overlay();
	public static final Cull CULL = new Cull();
	public static final DepthTest DEPTH_TEST = new DepthTest();
	public static final WriteMaskState WRITE_MASK_STATE = new WriteMaskState();
	public static final Layering LAYERING = new Layering();
	public static final Target TARGET = new Target();
	public static final LineWidth LINE_WIDTH = new LineWidth();

	public static class Transparencies {

		private Transparencies() {}

		public RenderPhase.Transparency without() {
			return RenderPhaseAccessor.getNoTransparency();
		}

		public RenderPhase.Transparency additive() {
			return RenderPhaseAccessor.getAdditiveTransparency();
		}

		public RenderPhase.Transparency lighting() {
			return RenderPhaseAccessor.getLightningTransparency();
		}

		public RenderPhase.Transparency glint() {
			return RenderPhaseAccessor.getGlintTransparency();
		}

		public RenderPhase.Transparency crumbling() {
			return RenderPhaseAccessor.getCrumblingTransparency();
		}

		public RenderPhase.Transparency translucent() {
			return RenderPhaseAccessor.getTranslucentTransparency();
		}
	}

	public static class Shader {

		private Shader() {}

		public RenderPhase.Shader without() {
			return RenderPhaseAccessor.getNoShader();
		}

		public RenderPhase.Shader block() {
			return RenderPhaseAccessor.getBlockShader();
		}

		public RenderPhase.Shader newEntity() {
			return RenderPhaseAccessor.getNewEntityShader();
		}

		public RenderPhase.Shader positionColorLightMap() {
			return RenderPhaseAccessor.getPositionColorLightMapShader();
		}

		public RenderPhase.Shader position() {
			return RenderPhaseAccessor.getPositionShader();
		}

		public RenderPhase.Shader positionColorTexture() {
			return RenderPhaseAccessor.getPositionColorTextureShader();
		}

		public RenderPhase.Shader positionTexture() {
			return RenderPhaseAccessor.getPositionTextureShader();
		}

		public RenderPhase.Shader positionColorTextureLightMap() {
			return RenderPhaseAccessor.getPositionColorTextureLightmapShader();
		}

		public RenderPhase.Shader color() {
			return RenderPhaseAccessor.getColorShader();
		}

		public RenderPhase.Shader solid() {
			return RenderPhaseAccessor.getSolidShader();
		}

		public RenderPhase.Shader cutoutMipped() {
			return RenderPhaseAccessor.getCutoutMippedShader();
		}

		public RenderPhase.Shader cutout() {
			return RenderPhaseAccessor.getCutoutShader();
		}

		public RenderPhase.Shader translucent() {
			return RenderPhaseAccessor.getTranslucentShader();
		}

		public RenderPhase.Shader translucentMovingBlock() {
			return RenderPhaseAccessor.getTranslucentMovingBlockShader();
		}

		public RenderPhase.Shader translucentNoCrumbling() {
			return RenderPhaseAccessor.getTranslucentNoCrumblingShader();
		}

		public RenderPhase.Shader armorCutoutNoCull() {
			return RenderPhaseAccessor.getArmorCutoutNoCullShader();
		}

		public RenderPhase.Shader entitySolid() {
			return RenderPhaseAccessor.getEntitySolidShader();
		}

		public RenderPhase.Shader entityCutout() {
			return RenderPhaseAccessor.getEntityCutoutShader();
		}

		public RenderPhase.Shader entityCutoutNonull() {
			return RenderPhaseAccessor.getEntityCutoutNonullShader();
		}

		public RenderPhase.Shader entityCutoutNonullOffsetZ() {
			return RenderPhaseAccessor.getEntityCutoutNonullOffsetZShader();
		}

		public RenderPhase.Shader itemEntityTranslucentCull() {
			return RenderPhaseAccessor.getItemEntityTranslucentCullShader();
		}

		public RenderPhase.Shader entityTranslucentCull() {
			return RenderPhaseAccessor.getEntityTranslucentCullShader();
		}

		public RenderPhase.Shader entityTranslucent() {
			return RenderPhaseAccessor.getEntityTranslucentShader();
		}

		public RenderPhase.Shader entityTranslucentEmissive() {
			return RenderPhaseAccessor.getEntityTranslucentEmissiveShader();
		}

		public RenderPhase.Shader entitySmoothCutout() {
			return RenderPhaseAccessor.getEntitySmoothCutoutShader();
		}

		public RenderPhase.Shader beaconBeam() {
			return RenderPhaseAccessor.getBeaconBeamShader();
		}

		public RenderPhase.Shader entityDecal() {
			return RenderPhaseAccessor.getEntityDecalShader();
		}

		public RenderPhase.Shader entityNoOutline() {
			return RenderPhaseAccessor.getEntityNoOutlineShader();
		}

		public RenderPhase.Shader entityShadow() {
			return RenderPhaseAccessor.getEntityShadowShader();
		}

		public RenderPhase.Shader entityAlpha() {
			return RenderPhaseAccessor.getEntityAlphaShader();
		}

		public RenderPhase.Shader eyes() {
			return RenderPhaseAccessor.getEyesShader();
		}

		public RenderPhase.Shader energySwirl() {
			return RenderPhaseAccessor.getEnergySwirlShader();
		}

		public RenderPhase.Shader leash() {
			return RenderPhaseAccessor.getLeashShader();
		}

		public RenderPhase.Shader waterMask() {
			return RenderPhaseAccessor.getWaterMaskShader();
		}

		public RenderPhase.Shader outline() {
			return RenderPhaseAccessor.getOutlineShader();
		}

		public RenderPhase.Shader armorGlint() {
			return RenderPhaseAccessor.getArmorGlintShader();
		}

		public RenderPhase.Shader armorEntityGlint() {
			return RenderPhaseAccessor.getArmorEntityGlintShader();
		}

		public RenderPhase.Shader translucentGlint() {
			return RenderPhaseAccessor.getTranslucentGlintShader();
		}

		public RenderPhase.Shader glint() {
			return RenderPhaseAccessor.getGlintShader();
		}

		public RenderPhase.Shader directGlint() {
			return RenderPhaseAccessor.getDirectGlintShader();
		}

		public RenderPhase.Shader entityGlint() {
			return RenderPhaseAccessor.getEntityGlintShader();
		}

		public RenderPhase.Shader directEntityGlint() {
			return RenderPhaseAccessor.getDirectEntityGlintShader();
		}

		public RenderPhase.Shader crumbling() {
			return RenderPhaseAccessor.getCrumblingShader();
		}

		public RenderPhase.Shader text() {
			return RenderPhaseAccessor.getTextShader();
		}

		public RenderPhase.Shader textIntensity() {
			return RenderPhaseAccessor.getTextIntensityShader();
		}

		public RenderPhase.Shader transparentText() {
			return RenderPhaseAccessor.getTransparentTextShader();
		}

		public RenderPhase.Shader transparentTextIntensity() {
			return RenderPhaseAccessor.getTransparentTextIntensityShader();
		}

		public RenderPhase.Shader lightning() {
			return RenderPhaseAccessor.getLightningShader();
		}

		public RenderPhase.Shader tripwire() {
			return RenderPhaseAccessor.getTripwireShader();
		}

		public RenderPhase.Shader endPortal() {
			return RenderPhaseAccessor.getEndPortalShader();
		}

		public RenderPhase.Shader endGateway() {
			return RenderPhaseAccessor.getEndGatewayShader();
		}

		public RenderPhase.Shader lines() {
			return RenderPhaseAccessor.getLinesShader();
		}
	}

	public static class Texture {

		private Texture() {}

		public RenderPhase.Texture mipmapBlockAtlas() {
			return RenderPhaseAccessor.getMipmapBlockAtlasTexture();
		}

		public RenderPhase.Texture blockAtlas() {
			return RenderPhaseAccessor.getBlockAtlasTexture();
		}
	}

	public static class TextureBase {

		private TextureBase() {}

		public RenderPhase.TextureBase without() {
			return RenderPhaseAccessor.getNoTexture();
		}
	}

	public static class Texturing {

		private Texturing() {}

		public RenderPhase.Texturing standard() {
			return RenderPhaseAccessor.getDefaultTexturing();
		}

		public RenderPhase.Texturing glint() {
			return RenderPhaseAccessor.getGlintTexturing();
		}

		public RenderPhase.Texturing entityGlint() {
			return RenderPhaseAccessor.getEntityGlintTexturing();
		}
	}

	public static class Lightmap {

		private Lightmap() {}

		public RenderPhase.Lightmap enable() {
			return RenderPhaseAccessor.getEnableLightmap();
		}

		public RenderPhase.Lightmap disable() {
			return RenderPhaseAccessor.getDisableLightmap();
		}
	}

	public static class Overlay {

		private Overlay() {}

		public RenderPhase.Overlay enableColor() {
			return RenderPhaseAccessor.getEnableOverlayColor();
		}

		public RenderPhase.Overlay disableColor() {
			return RenderPhaseAccessor.getDisableOverlayColor();
		}
	}

	public static class Cull {

		private Cull() {}

		public RenderPhase.Cull enable() {
			return RenderPhaseAccessor.getEnableCulling();
		}

		public RenderPhase.Cull disable() {
			return RenderPhaseAccessor.getDisableCulling();
		}
	}

	public static class DepthTest {

		private DepthTest() {}

		public RenderPhase.DepthTest always() {
			return RenderPhaseAccessor.getAlwaysDepthTest();
		}

		public RenderPhase.DepthTest equal() {
			return RenderPhaseAccessor.getEqualDepthTest();
		}

		public RenderPhase.DepthTest lequal() {
			return RenderPhaseAccessor.getLequalDepthTest();
		}
	}

	public static class WriteMaskState {

		private WriteMaskState() {}

		public RenderPhase.WriteMaskState all() {
			return RenderPhaseAccessor.getAllMask();
		}

		public RenderPhase.WriteMaskState color() {
			return RenderPhaseAccessor.getColorMask();
		}

		public RenderPhase.WriteMaskState depth() {
			return RenderPhaseAccessor.getDepthMask();
		}
	}

	public static class Layering {

		private Layering() {}

		public RenderPhase.Layering without() {
			return RenderPhaseAccessor.getNoLayering();
		}

		public RenderPhase.Layering polygonOffset() {
			return RenderPhaseAccessor.getPolygonOffsetLayering();
		}

		public RenderPhase.Layering viewOffsetZ() {
			return RenderPhaseAccessor.getViewOffsetZLayering();
		}
	}

	public static class Target {

		private Target() {}

		public RenderPhase.Target major() {
			return RenderPhaseAccessor.getMainTarget();
		}

		public RenderPhase.Target outline() {
			return RenderPhaseAccessor.getOutlineTarget();
		}

		public RenderPhase.Target translucent() {
			return RenderPhaseAccessor.getTranslucentTarget();
		}

		public RenderPhase.Target particles() {
			return RenderPhaseAccessor.getParticlesTarget();
		}

		public RenderPhase.Target weather() {
			return RenderPhaseAccessor.getWeatherTarget();
		}

		public RenderPhase.Target clouds() {
			return RenderPhaseAccessor.getCloudsTarget();
		}

		public RenderPhase.Target item() {
			return RenderPhaseAccessor.getItemTarget();
		}
	}

	public static class LineWidth {

		private LineWidth() {}

		public RenderPhase.LineWidth full() {
			return RenderPhaseAccessor.getFullLineWidth();
		}
	}
}
