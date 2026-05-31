package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.core.api.serialization.MModdingDecoders;
import com.mojang.serialization.*;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class LayerDefinitionDecoders {

	private static final Decoder<CubeDeformation> CUBE_DEFORMATION = MModdingDecoders.alternative(
		MModdingCodecs.VECTOR3F.map(vec -> new CubeDeformation(vec.x, vec.y, vec.z)),
		Codec.FLOAT.map(CubeDeformation::new)
	);

	public static final Decoder<CubeListBuilder> CUBE_LIST_BUILDER_DECODER = MModdingDecoders.listOf(CubeBox.DECODER).map(cubes -> {
		CubeListBuilder builder = CubeListBuilder.create();
		for (CubeBox cube : cubes) {
			cube.apply(builder);
		}
		return builder;
	});

	public static final Decoder<PartPose> PART_POSE_DECODER = MModdingDecoders.recordDecoder(
		MModdingCodecs.VECTOR3F.fieldOf("offset"),
		MModdingCodecs.VECTOR3F.fieldOf("rotation"),
		MModdingCodecs.VECTOR3F.optionalFieldOf("scale", new Vector3f(1.0f, 1.0f, 1.0f)),
		(offset, rotation, scale) -> PartPose.offsetAndRotation(offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z).scaled(scale.x, scale.y, scale.z)
	);

	public static final Decoder<PartDefinition> PART_DEFINITION_DECODER = MModdingDecoders.recordDecoder(
		CUBE_LIST_BUILDER_DECODER.fieldOf("cubes"),
		PART_POSE_DECODER.fieldOf("pose"),
		MModdingDecoders.optionalFieldOf(MModdingDecoders.compoundList(Codec.STRING, MModdingDecoders.lazyInitialized(() -> LayerDefinitionDecoders.PART_DEFINITION_DECODER)), "children"),
		(cubes, pose, optional) -> {
			PartDefinition result = new PartDefinition(cubes.getCubes(), pose);
			optional.ifPresent(children -> children.forEach(result::addOrReplaceChild));
			return result;
		}
	);

	public static final Decoder<LayerDefinition> LAYER_DEFINITION_DECODER = MModdingDecoders.recordDecoder(
		MModdingDecoders.compoundList(Codec.STRING, PART_DEFINITION_DECODER).fieldOf("parts"),
		MModdingCodecs.VECTOR2I.fieldOf("texture_size"),
		(parts, textureSize) -> {
			MeshDefinition mesh = new MeshDefinition();
			PartDefinition root = mesh.getRoot();
			parts.forEach(root::addOrReplaceChild);
			return LayerDefinition.create(mesh, textureSize.x, textureSize.y);
		}
	);

	public record CubeBox(Vector2i offset, boolean mirror, Vector3f anchor, Vector3f size, CubeDeformation deformation) {

		private static final Decoder<CubeBox> DECODER = MModdingDecoders.recordDecoder(
			MModdingCodecs.VECTOR2I.fieldOf("texture_offset"),
			Codec.BOOL.optionalFieldOf("mirror", false),
			MModdingCodecs.VECTOR3F.fieldOf("anchor"),
			MModdingCodecs.VECTOR3F.fieldOf("size"),
			MModdingDecoders.optionalFieldOf(CUBE_DEFORMATION, "deformation", new CubeDeformation(0.0f)),
			CubeBox::new
		);

		void apply(CubeListBuilder builder) {
			builder.texOffs(this.offset.x, this.offset.y)
				.mirror(this.mirror)
				.addBox(this.anchor.x, this.anchor.y, this.anchor.z, this.size.x, this.size.y, this.size.z, this.deformation);
		}
	}
}
