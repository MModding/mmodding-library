package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.entities.projectiles.StuckArrowDisplay;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.util.Identifier;

import java.util.List;

@InternalOf(StuckArrowDisplay.class)
public interface LivingEntityDuckInterface {

	List<Identifier> mmodding_lib$getStuckArrowTypes();

	void mmodding_lib$setStuckArrowTypes(List<Identifier> stuckArrowTypes);

	void mmodding_lib$addStuckArrowType(Identifier arrowEntityId);
}
