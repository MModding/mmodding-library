package com.mmodding.mmodding_lib.library.events.networking;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

public class LivingEntityNetworkingEvents {

	public static final Event<BeforeStuckArrowTypes> BEFORE_STUCK_ARROW_TYPES = Event.create(BeforeStuckArrowTypes.class, callbacks -> (livingEntity, stuckArrowTypes) -> {
		for (BeforeStuckArrowTypes callback : callbacks) {
			callback.beforeStuckArrowTypesSent(livingEntity, stuckArrowTypes);
		}
	});

	public static final Event<AfterStuckArrowTypes> AFTER_STUCK_ARROW_TYPES = Event.create(AfterStuckArrowTypes.class, callbacks -> (livingEntity, stuckArrowTypes) -> {
		for (AfterStuckArrowTypes callback : callbacks) {
			callback.afterStuckArrowTypesSent(livingEntity, stuckArrowTypes);
		}
	});

	@FunctionalInterface
	public interface BeforeStuckArrowTypes {

		void beforeStuckArrowTypesSent(LivingEntity livingEntity, Map<Integer, Identifier> stuckArrowTypes);
	}

	@FunctionalInterface
	public interface AfterStuckArrowTypes {

		void afterStuckArrowTypesSent(LivingEntity livingEntity, Map<Integer, Identifier> stuckArrowTypes);
	}
}
