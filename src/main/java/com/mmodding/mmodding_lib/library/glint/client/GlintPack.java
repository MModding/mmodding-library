package com.mmodding.mmodding_lib.library.glint.client;

import com.mmodding.mmodding_lib.library.client.render.layer.RenderLayerElements;
import com.mmodding.mmodding_lib.library.client.utils.RenderLayerUtils;
import com.mmodding.mmodding_lib.library.utils.TextureLocation;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents;

@ClientOnly
public class GlintPack {

	@Nullable
	private final RenderLayer armor;

	@Nullable
	private final RenderLayer armorEntity;

	@Nullable
	private final RenderLayer translucent;

	@Nullable
	private final RenderLayer base;

	@Nullable
	private final RenderLayer direct;

	@Nullable
	private final RenderLayer entity;

	@Nullable
	private final RenderLayer directEntity;

	public GlintPack(TextureLocation texture, boolean armor, boolean armorEntity, boolean translucent, boolean base, boolean direct, boolean entity, boolean directEntity) {
		this.armor = GlintPack.createArmor(texture, armor);
		this.armorEntity = GlintPack.createArmorEntity(texture, armorEntity);
		this.translucent = GlintPack.createTranslucent(texture, translucent);
		this.base = GlintPack.createBase(texture, base);
		this.direct = GlintPack.createDirect(texture, direct);
		this.entity = GlintPack.createEntity(texture, entity);
		this.directEntity = GlintPack.createDirectEntity(texture, directEntity);
	}

	public static GlintPack create(TextureLocation texture) {
		return new GlintPack(texture, true, true, true, true, true, true, true);
	}

	@Nullable
	public static RenderLayer createArmor(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			"armor_" + texture.getPath(),
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.armorGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.glint())
				.layering(RenderLayerElements.LAYERING.viewOffsetZ())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createArmorEntity(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			"armor_entity_" + texture.getPath(),
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.armorEntityGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.entityGlint())
				.layering(RenderLayerElements.LAYERING.viewOffsetZ())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createTranslucent(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			texture.getPath() + "_translucent",
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.translucentGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.glint())
				.target(RenderLayerElements.TARGET.item())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createBase(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			texture.getPath(),
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.glint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.glint())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createDirect(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			texture.getPath() + "_direct",
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.directGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.glint())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createEntity(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			"entity_" + texture.getPath(),
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.entityGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.target(RenderLayerElements.TARGET.item())
				.texturing(RenderLayerElements.TEXTURING.entityGlint())
				.build(false)
		) : null;
	}

	@Nullable
	public static RenderLayer createDirectEntity(TextureLocation texture, boolean canCreate) {
		return canCreate ? RenderLayerUtils.of(
			"entity_" + texture.getPath() + "_direct",
			VertexFormats.POSITION_TEXTURE,
			VertexFormat.DrawMode.QUADS,
			256,
			RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderLayerElements.SHADER.directEntityGlint())
				.texture(new RenderPhase.Texture(texture, true, false))
				.writeMaskState(RenderLayerElements.WRITE_MASK_STATE.color())
				.cull(RenderLayerElements.CULL.disable())
				.depthTest(RenderLayerElements.DEPTH_TEST.equal())
				.transparency(RenderLayerElements.TRANSPARENCY.glint())
				.texturing(RenderLayerElements.TEXTURING.entityGlint())
				.build(false)
		) : null;
	}

	public void register(Identifier identifier) {
		RenderLayerUtils.addGlintPack(identifier, this);
		ClientLifecycleEvents.READY.register(client -> {
			if (this.armor != null) RenderLayerUtils.addEntityBuilder(this.armor);
			if (this.armorEntity != null) RenderLayerUtils.addEntityBuilder(this.armorEntity);
			if (this.translucent != null) RenderLayerUtils.addEntityBuilder(this.translucent);
			if (this.base != null) RenderLayerUtils.addEntityBuilder(this.base);
			if (this.direct != null) RenderLayerUtils.addEntityBuilder(this.direct);
			if (this.entity != null) RenderLayerUtils.addEntityBuilder(this.entity);
			if (this.directEntity != null) RenderLayerUtils.addEntityBuilder(this.directEntity);
		});
	}

	@Nullable
	public RenderLayer getArmor() {
		return this.armor;
	}

	@Nullable
	public RenderLayer getArmorEntity() {
		return this.armorEntity;
	}

	@Nullable
	public RenderLayer getTranslucent() {
		return this.translucent;
	}

	@Nullable
	public RenderLayer getBase() {
		return this.base;
	}

	@Nullable
	public RenderLayer getDirect() {
		return this.direct;
	}

	@Nullable
	public RenderLayer getEntity() {
		return this.entity;
	}

	@Nullable
	public RenderLayer getDirectEntity() {
		return this.directEntity;
	}

	public VertexConsumer getArmorConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
		if (glint && this.getArmor() != null && this.getArmorEntity() != null) {
			return VertexConsumers.union(provider.getBuffer(solid ? this.getArmor() : this.getArmorEntity()), provider.getBuffer(layer));
		}
		else {
			return provider.getBuffer(layer);
		}
	}

	public VertexConsumer getCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
		if (this.getBase() != null) {
			return VertexConsumers.union(
				new OverlayVertexConsumer(provider.getBuffer(this.getBase()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer)
			);
		}
		else {
			return provider.getBuffer(layer);
		}
	}

	public VertexConsumer getDirectCompassConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
		if (this.getDirect() != null) {
			return VertexConsumers.union(
				new OverlayVertexConsumer(provider.getBuffer(this.getDirect()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer)
			);
		}
		else {
			return provider.getBuffer(layer);
		}
	}

	public VertexConsumer getItemConsumer(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint) {
		if (glint && this.getTranslucent() != null && this.getBase() != null && this.getEntity() != null) {
			if (MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull()) {
				return VertexConsumers.union(vertexConsumers.getBuffer(this.getTranslucent()), vertexConsumers.getBuffer(layer));
			}
			else {
				return VertexConsumers.union(vertexConsumers.getBuffer(solid ? this.getBase() : this.getEntity()), vertexConsumers.getBuffer(layer));
			}
		} else {
			return vertexConsumers.getBuffer(layer);
		}
	}

	public VertexConsumer getDirectItemConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
		if (glint && (solid ? this.getDirect() != null : this.getDirectEntity() != null)) {
			return VertexConsumers.union(provider.getBuffer(solid ? this.getDirect() : this.getDirectEntity()), provider.getBuffer(layer));
		}
		else {
			return provider.getBuffer(layer);
		}
	}
}
