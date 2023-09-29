package com.mmodding.mmodding_lib.ducks;

import net.minecraft.util.Identifier;

import java.util.Map;

public interface LivingEntityDuckInterface {

	Map<Integer, Identifier> mmodding_lib$getStuckArrowTypes();

	void mmodding_lib$setStuckArrowTypes(Map<Integer, Identifier> stuckArrowTypes);

	void mmodding_lib$putStuckArrowType(int index, Identifier arrowEntityId);
}
