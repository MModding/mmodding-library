package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.ducks.LivingEntityDuckInterface;
import com.mmodding.mmodding_lib.library.entities.projectiles.StuckArrowDisplay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(StuckArrowsFeatureRenderer.class)
public class StuckArrowsFeatureRendererMixin<T extends LivingEntity> extends StuckObjectsFeatureRendererMixin<T> {

	@Shadow
	@Final
	private EntityRenderDispatcher dispatcher;

	@Inject(method = "renderObject", at = @At(value = "HEAD"), cancellable = true)
	private void renderObject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float directionX, float directionY, float directionZ, float tickDelta, CallbackInfo ci) {
		if (entity instanceof LivingEntity livingEntity) {
			List<Identifier> stuckArrowTypes = ((LivingEntityDuckInterface) livingEntity).mmodding_lib$getStuckArrowTypes();
			Identifier identifier = stuckArrowTypes.size() > this.currentIndex ? stuckArrowTypes.get(this.currentIndex) : new Identifier("arrow");
			Entity check = Registry.ENTITY_TYPE.get(identifier).create(entity.world);
			if (check != null) {
				if (check instanceof StuckArrowDisplay<?> display) {
					float f = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
					PersistentProjectileEntity persistentProjectileEntity = display.getArrowInstance(entity.world, entity.getX(), entity.getY(), entity.getZ());
					persistentProjectileEntity.setYaw((float) (Math.atan2(directionX, directionZ) * 180.0f / Math.PI));
					persistentProjectileEntity.setPitch((float) (Math.atan2(directionY, f) * 180.0f / Math.PI));
					persistentProjectileEntity.prevYaw = persistentProjectileEntity.getYaw();
					persistentProjectileEntity.prevPitch = persistentProjectileEntity.getPitch();
					this.dispatcher.render(persistentProjectileEntity, 0.0, 0.0, 0.0, 0.0f, tickDelta, matrices, vertexConsumers, light);
					ci.cancel();
				}
				check.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}
}
