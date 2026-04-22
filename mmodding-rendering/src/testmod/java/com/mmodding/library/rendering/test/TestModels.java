package com.mmodding.library.rendering.test;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TestModels {

	public static LayerDefinition createTestCapLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -9.0F, 4.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 12.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 23.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createTestSuitLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -9.0F, 4.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 12.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 23.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leftSleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -9.0F, 4.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 12.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 23.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rightSleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -9.0F, 4.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 12.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-4.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(16, 0).addBox(-12.0F, -9.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 23.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
