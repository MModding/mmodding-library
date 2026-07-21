package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class EntityModelReloader extends SimpleJsonResourceReloadListener<LayerDefinition> {

	public static final EntityModelReloader INSTANCE = new EntityModelReloader(FileToIdConverter.json("mmodding/models/entity"));

	private final Map<Identifier, SimpleEntityModel<? extends EntityRenderState>> models = new Object2ObjectOpenHashMap<>();

	protected EntityModelReloader(FileToIdConverter lister) {
		super(MModdingCodecs.decodeOnly(LayerDefinitionDecoders.LAYER_DEFINITION_DECODER), lister);
	}

	@Override
	protected void apply(Map<Identifier, LayerDefinition> preparations, ResourceManager manager, ProfilerFiller profiler) {
		this.models.clear();
		preparations.forEach((identifier, definition) -> this.models.put(identifier, new SimpleEntityModel<>(definition.bakeRoot())));
		DataDrivenModelEvents.FINALIZE_ENTITY_MODELS.invoker().execute(new EntityModelGetterImpl(this.models));
	}

	public SimpleEntityModel<? extends EntityRenderState> getModel(Identifier identifier) {
		return this.models.get(identifier);
	}
}
