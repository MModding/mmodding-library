package com.mmodding.library.resource.api.client.model.data;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.resource.api.client.model.SimpleBlockEntityModel;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import dev.yumi.commons.event.Event;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

/**
 * Events allowing to interact with the loading of data driven model resources.
 */
public final class DataDrivenModelEvents {

	private DataDrivenModelEvents() {}

	/**
	 * @see FinalizeEntityModels#execute(FinalizeEntityModels.ModelGetter, boolean)
	 */
	public static final Event<Identifier, FinalizeEntityModels> FINALIZE_ENTITY_MODELS = MModdingLibrary.getEventManager().create(FinalizeEntityModels.class);

	/**
	 * @see FinalizeBlockEntityModels#execute(FinalizeBlockEntityModels.ModelGetter, boolean)
	 */
	public static final Event<Identifier, FinalizeBlockEntityModels> FINALIZE_BLOCK_ENTITY_MODELS = MModdingLibrary.getEventManager().create(FinalizeBlockEntityModels.class);

	@FunctionalInterface
	public interface FinalizeEntityModels {

		void execute(ModelGetter models, boolean isInitialLoad);

		@ApiStatus.NonExtendable
		interface ModelGetter {

			<S extends EntityRenderState> SimpleEntityModel<S> getModel(Identifier identifier);
		}
	}

	@FunctionalInterface
	public interface FinalizeBlockEntityModels {

		void execute(ModelGetter models, boolean isInitialLoad);

		@ApiStatus.NonExtendable
		interface ModelGetter {

			<S extends BlockEntityRenderState> SimpleBlockEntityModel<S> getModel(Identifier identifier);
		}
	}
}
