package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.serialization.MModdingCodecs;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleBlockEntityModel;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
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

	private final Map<Identifier, SimpleBlockEntityModel<? extends BlockEntityRenderState>> models = new Object2ObjectOpenHashMap<>();

	protected BlockEntityModelReloader(FileToIdConverter lister) {
		super(MModdingCodecs.decodeOnly(LayerDefinitionDecoders.LAYER_DEFINITION_DECODER), lister);
	}

	@Override
	protected void apply(Map<Identifier, LayerDefinition> preparations, ResourceManager manager, ProfilerFiller profiler) {
		this.models.clear();
		preparations.forEach((identifier, definition) -> this.models.put(identifier, new SimpleBlockEntityModel<>(definition.bakeRoot())));
		DataDrivenModelEvents.FINALIZE_BLOCK_ENTITY_MODELS.invoker().execute(new BlockEntityModelGetterImpl(this.models));
	}
}
