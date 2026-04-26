package com.mmodding.library.integration.trinkets.test;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TestModels {

	public static LayerDefinition createTestCapLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createTestSuitLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(16, 14).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 16.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createTestPantsLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition junction = partdefinition.addOrReplaceChild("junction", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

		PartDefinition left_legging = partdefinition.addOrReplaceChild("left_legging", CubeListBuilder.create().texOffs(16, 6).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, 0.0F));

		PartDefinition right_legging = partdefinition.addOrReplaceChild("right_legging", CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createTestShoesLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_shoe = partdefinition.addOrReplaceChild("left_shoe", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 0.0F));

		PartDefinition right_shoe = partdefinition.addOrReplaceChild("right_shoe", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
