package com.mmodding.mmodding_lib.library.events.networking.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

@ClientOnly
public class ClientLivingEntityNetworkingEvents {

	public static final Event<BeforeStuckArrowTypes> BEFORE_STUCK_ARROW_TYPES = Event.create(BeforeStuckArrowTypes.class, callbacks -> (livingEntity, stuckArrowTypes) -> {
		for (BeforeStuckArrowTypes callback : callbacks) {
			callback.beforeStuckArrowTypesReceived(livingEntity, stuckArrowTypes);
		}
	});

	public static final Event<AfterStuckArrowTypes> AFTER_STUCK_ARROW_TYPES = Event.create(AfterStuckArrowTypes.class, callbacks -> (livingEntity, stuckArrowTypes) -> {
		for (AfterStuckArrowTypes callback : callbacks) {
			callback.afterStuckArrowTypesReceived(livingEntity, stuckArrowTypes);
		}
	});

	@FunctionalInterface
	public interface BeforeStuckArrowTypes {

		void beforeStuckArrowTypesReceived(LivingEntity livingEntity, Map<Integer, Identifier> stuckArrowTypes);
	}

	@FunctionalInterface
	public interface AfterStuckArrowTypes {

		void afterStuckArrowTypesReceived(LivingEntity livingEntity, Map<Integer, Identifier> stuckArrowTypes);
	}
}
