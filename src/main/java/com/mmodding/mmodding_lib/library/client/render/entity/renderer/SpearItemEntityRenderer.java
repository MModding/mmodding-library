package com.mmodding.mmodding_lib.library.client.render.entity.renderer;

import com.mmodding.mmodding_lib.library.entities.projectiles.SpearEntity;
import com.mmodding.mmodding_lib.mixin.accessors.TridentEntityAccessor;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SpearItemEntityRenderer<T extends SpearEntity> extends EntityRenderer<T> {

	private final ItemRenderer itemRenderer;

	public SpearItemEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public void render(T spearEntity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		ItemStack stack = ((TridentEntityAccessor) spearEntity).getTridentStack().copy();
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, spearEntity.prevYaw, spearEntity.getYaw()) - 90.0f));
		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, spearEntity.prevPitch, spearEntity.getPitch()) - 135f + this.getAdditionalPitch()));
		this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, this.itemRenderer.getHeldItemModel(stack, spearEntity.getWorld(), null, 0));
	}

	public float getAdditionalPitch() {
		return 0;
	}

	@Override
	public Identifier getTexture(T spearEntity) {
		return null;
	}
}
