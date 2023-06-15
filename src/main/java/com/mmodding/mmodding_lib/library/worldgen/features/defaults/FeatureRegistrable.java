package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.function.Predicate;

public interface FeatureRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomFeature customFeature && this.isNotRegistered()) {
			Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, identifier, customFeature.getConfiguredFeature());
			Registry.register(BuiltinRegistries.PLACED_FEATURE, identifier, customFeature.getPlacedFeature());
			this.setRegistered();
			this.setIdentifier(identifier);
		}
	}

	void addToBiomes(Predicate<BiomeSelectionContext> ctx);

	void setIdentifier(Identifier identifier);
}
