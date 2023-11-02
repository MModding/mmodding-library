package com.mmodding.mmodding_lib.library.pois;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mmodding.mmodding_lib.mixin.accessors.PointOfInterestTypesAccessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;

public interface POIRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomPOI POI && this.isNotRegistered()) {
			RegistryKey<PointOfInterestType> key = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, identifier);
			RegistrationUtils.registerPointOfInterestType(identifier, POI.getType());
			PointOfInterestTypesAccessor.invokeAddStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(key));
			this.setRegistered();
		}
	}
}
