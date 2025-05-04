package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.Registrable;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;

import java.util.function.Predicate;

public interface FeatureRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomFeature customFeature && this.isNotRegistered()) {
			this.setIdentifier(identifier);
			Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, identifier, customFeature.getConfiguredFeature());
			Registry.register(BuiltinRegistries.PLACED_FEATURE, identifier, customFeature.getDefaultPlacedFeature());
			customFeature.getAdditionalPlacedFeatures().forEach(
				(feature, string) -> Registry.register(BuiltinRegistries.PLACED_FEATURE, IdentifierUtils.extend(identifier, string), feature)
			);
			this.setRegistered();
		}
	}

	void addDefaultToBiomes(Predicate<BiomeSelectionContext> ctx);

	void addAdditionalToBiomes(Predicate<BiomeSelectionContext> ctx, String idExt);

	void setIdentifier(Identifier identifier);
}
