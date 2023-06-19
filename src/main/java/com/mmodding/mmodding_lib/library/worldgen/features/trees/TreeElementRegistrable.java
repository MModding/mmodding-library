package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.util.Identifier;

public interface TreeElementRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomTrunkPlacer customTrunkPlacer && this.isNotRegistered()) {
			RegistrationUtils.registerTrunkPlacerType(identifier, customTrunkPlacer.getType());
			this.setRegistered();
		}
		else if (this instanceof CustomFoliagePlacer customFoliagePlacer && this.isNotRegistered()) {
			RegistrationUtils.registerFoliagePlacerType(identifier, customFoliagePlacer.getType());
			this.setRegistered();
		}
		else if (this instanceof CustomTreeDecorator customTreeDecorator && this.isNotRegistered()) {
			RegistrationUtils.registerTreeDecoratorType(identifier, customTreeDecorator.getType());
			this.setRegistered();
		}
		else if (this instanceof CustomRootPlacer customRootPlacer && this.isNotRegistered()) {
			RegistrationUtils.registerRootPlacerType(identifier, customRootPlacer.getType());
			this.setRegistered();
		}
	}
}
