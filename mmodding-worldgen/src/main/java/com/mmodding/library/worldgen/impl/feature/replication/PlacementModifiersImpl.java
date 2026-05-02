package com.mmodding.library.worldgen.impl.feature.replication;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class PlacementModifiersImpl implements PlacementModifiers {

	private final List<PlacementModifier> placementModifiers;

	public PlacementModifiersImpl(List<PlacementModifier> placementModifiers) {
		this.placementModifiers = placementModifiers;
	}

	public List<PlacementModifier> fetch(PlacementModifierType<?> type) {
		List<PlacementModifier> fetched = new ArrayList<>();
		this.placementModifiers.forEach(modifier -> {
			if (modifier.type() == type) {
				fetched.add(modifier);
			}
		});
		return fetched;
	}

	public PlacementModifier fetchFirst(PlacementModifierType<?> type) {
		return this.fetch(type).get(0);
	}

	public PlacementModifiers add(PlacementModifier... modifiers) {
		this.add(List.of(modifiers));
		return this;
	}

	public PlacementModifiers add(List<PlacementModifier> modifiers) {
		this.placementModifiers.addAll(modifiers);
		return this;
	}

	public PlacementModifiers remove(PlacementModifierType<?> type) {
		for (int i = 0; i < this.placementModifiers.size(); i++) {
			if (this.placementModifiers.get(i).type() == type) {
				this.placementModifiers.remove(i);
				i--;
			}
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <P extends PlacementModifier> PlacementModifiers mutateTypeTo(PlacementModifierType<P> type, AutoMapper<P> mutator) {
		P modifier = (P) this.fetchFirst(type);
		PlacementModifier mutated = mutator.map(modifier);
		this.remove(type);
		this.add(mutated);
		return this;
	}

	public List<PlacementModifier> retrieve() {
		return this.placementModifiers;
	}
}
