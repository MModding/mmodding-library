package com.mmodding.library.rendering.api.cosmetic.renderer;

import com.mmodding.library.rendering.api.cosmetic.Cosmetic;
import com.mmodding.library.rendering.api.model.SimpleEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rendering {@link Cosmetic} stuff.
 */
public abstract class CosmeticRenderer implements ArmorRenderer {

	private final Cosmetic cosmetic;
	private final Map<String, EntityModel<HumanoidRenderState>> models;

	public CosmeticRenderer(Cosmetic cosmetic, EntityRendererProvider.Context context) {
		this.cosmetic = cosmetic;
		this.models = cosmetic.getModelFactories().entrySet()
			.stream()
			.map(entry -> Map.entry(entry.getKey(), entry.getValue().createModel(context)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	protected boolean isSlim(HumanoidRenderState state) {
		return state instanceof AvatarRenderState avatar && avatar.skin.model().equals(PlayerModelType.SLIM);
	}

	protected EntityModel<HumanoidRenderState> getModel(ItemStack stack, boolean isSlim) {
		return this.models.get(this.cosmetic.getModel(stack, isSlim));
	}

	protected Identifier getTexture(ItemStack stack, boolean isSlim) {
		return this.cosmetic.getTexture(stack, isSlim);
	}

	@Nullable
	protected EntityModel<HumanoidRenderState> getSubModel(EntityModel<HumanoidRenderState> model, String sub) {
		ModelPart subPart = model.getChildPart(sub);
		return subPart != null ? new SimpleEntityModel<>(subPart) : null;
	}

	@Override
	public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) {
		return false;
	}

	/**
	 * A factory to create an {@link CosmeticRenderer} instance.
	 * @see ArmorRenderer.Factory
	 */
	@FunctionalInterface
	public interface Factory {

		CosmeticRenderer createCosmeticRenderer(Cosmetic cosmetic, EntityRendererProvider.Context context);
	}
}
