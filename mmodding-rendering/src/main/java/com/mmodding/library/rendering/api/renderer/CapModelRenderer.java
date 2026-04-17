package com.mmodding.library.rendering.api.renderer;

import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.rendering.api.AccessoryInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Rendering {@link AccessoryInfo} stuff as a cap-like accessory.
 */
public class CapModelRenderer implements ArmorRenderer {

	private final BiList<Predicate<ItemStack>, AccessoryInfo> rules;
	private final Map<AccessoryInfo, EntityModel<HumanoidRenderState>> models;

	/**
	 * Makes use of one model wrapped in a {@link AccessoryInfo}.
	 * @param info the accessory info
	 * @param context the context
	 */
	public CapModelRenderer(AccessoryInfo info, EntityRendererProvider.Context context) {
		this(BiList.of(ignored -> true, info), context);
	}

	/**
	 * Makes use of multiple models wrapped in {@link AccessoryInfo} objects.
	 * This is interesting when you want the model to depend on the item stack status.
	 * @param rules a list of rules: if the predicate passes, the accessory is selected
	 * @param context the context
	 */
	public CapModelRenderer(BiList<Predicate<ItemStack>, AccessoryInfo> rules, EntityRendererProvider.Context context) {
		this.rules = rules;
		this.models = new Object2ObjectOpenHashMap<>();
		rules.forEachSecond(info -> this.models.put(info, info.createModel(context)));
	}

	private AccessoryInfo selectInfo(ItemStack stack) {
		for (Pair<Predicate<ItemStack>, AccessoryInfo> rule : rules) {
			if (rule.first().test(stack)) {
				return rule.second();
			}
		}
		throw new IllegalStateException("No matching rule in CapModelRenderer");
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		AccessoryInfo info = this.selectInfo(stack);
		EntityModel<HumanoidRenderState> modelToRender = this.models.get(info);
		RenderType renderType = modelToRender.renderType(info.getTexture());
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		contextModel.translateToHead(poseStack);
		// state.eyeHeight changes with the pose, but the translation we want is always the standard eye height
		poseStack.translate(Vec3.Y_AXIS.scale(-state.entityType.getDimensions().eyeHeight()));
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f);
		submitNodeCollector.submitModel(modelToRender, state, poseStack, renderType, state.lightCoords, overlayCoords, state.outlineColor, null);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) {
		return false;
	}
}
