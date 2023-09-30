package com.mmodding.mmodding_lib.ducks;

import net.minecraft.util.Identifier;

import java.util.List;

public interface LivingEntityDuckInterface {

	List<Identifier> mmodding_lib$getStuckArrowTypes();

	void mmodding_lib$setStuckArrowTypes(List<Identifier> stuckArrowTypes);

	void mmodding_lib$addStuckArrowType(Identifier arrowEntityId);
}
