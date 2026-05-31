package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
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

	private final LiteRegistry<SimpleEntityModel<? extends EntityRenderState>> models = LiteRegistry.create();

	private boolean initial = true;

	protected EntityModelReloader(FileToIdConverter lister) {
		super(MModdingCodecs.decodeOnly(LayerDefinitionDecoders.LAYER_DEFINITION_DECODER), lister);
	}

	@Override
	protected void apply(Map<Identifier, LayerDefinition> preparations, ResourceManager manager, ProfilerFiller profiler) {
		preparations.forEach((identifier, definition) -> this.models.register(identifier, new SimpleEntityModel<>(definition.bakeRoot())));
		DataDrivenModelEvents.FINALIZE_ENTITY_MODELS.invoker().execute(new EntityModelGetterImpl(this.models), this.initial);
		this.initial = false;
	}
}
