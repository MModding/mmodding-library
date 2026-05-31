package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleBlockEntityModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class BlockEntityModelReloader extends SimpleJsonResourceReloadListener<LayerDefinition> {

	public static final BlockEntityModelReloader INSTANCE = new BlockEntityModelReloader(FileToIdConverter.json("mmodding/models/block_entity"));

	private final LiteRegistry<SimpleBlockEntityModel<? extends BlockEntityRenderState>> models = LiteRegistry.create();

	private boolean initial = true;

	protected BlockEntityModelReloader(FileToIdConverter lister) {
		super(MModdingCodecs.decodeOnly(LayerDefinitionDecoders.LAYER_DEFINITION_DECODER), lister);
	}

	@Override
	protected void apply(Map<Identifier, LayerDefinition> preparations, ResourceManager manager, ProfilerFiller profiler) {
		preparations.forEach((identifier, definition) -> this.models.register(identifier, new SimpleBlockEntityModel<>(definition.bakeRoot())));
		DataDrivenModelEvents.FINALIZE_BLOCK_ENTITY_MODELS.invoker().execute(new BlockEntityModelGetterImpl(this.models), this.initial);
		this.initial = false;
	}
}
