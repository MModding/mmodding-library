package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.pois.CustomPOI;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.function.Consumer;

public class CustomPortalLink {

	protected final RegistryKey<World> worldKey;
	protected final RegistryKey<PointOfInterestType> poiKey;
	protected final Consumer<CustomPortalBlock> registration;

	protected CustomPortalLink(RegistryKey<World> worldKey, RegistryKey<PointOfInterestType> poiKey, Consumer<CustomPortalBlock> registration) {
		this.worldKey = worldKey;
		this.poiKey = poiKey;
		this.registration = registration;
	}

	public static CustomPortalLink create(Identifier dimensionIdentifier) {

		Identifier poiId = new Identifier(dimensionIdentifier.getNamespace(), dimensionIdentifier.getPath() + "_portal");

		RegistryKey<World> worldKey = RegistryKey.of(Registry.WORLD_KEY, dimensionIdentifier);
		RegistryKey<PointOfInterestType> poiKey = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, poiId);

		return new CustomPortalLink(worldKey, poiKey, (portalBlock -> new CustomPOI(portalBlock.getBlock(), 0, 1).register(poiId)));
	}

	void register(CustomPortalBlock portalBlock) {
		this.registration.accept(portalBlock);
	}

	public RegistryKey<World> getWorldKey() {
		return this.worldKey;
	}

	public RegistryKey<PointOfInterestType> getPoiKey() {
		return this.poiKey;
	}
}
