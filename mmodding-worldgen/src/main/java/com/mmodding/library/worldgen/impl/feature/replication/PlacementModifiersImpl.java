package com.mmodding.library.worldgen.impl.feature.replication;

import com.mmodding.library.worldgen.api.feature.replication.PlacementModifiers;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PlacementModifiersImpl implements PlacementModifiers {

	private final List<PlacementModifier> placementModifiers;

	public PlacementModifiersImpl(List<PlacementModifier> placementModifiers) {
		this.placementModifiers = placementModifiers;
	}

	public List<PlacementModifier> fetch(PlacementModifierType<?> type) {
		List<PlacementModifier> fetched = new ArrayList<>();
		this.placementModifiers.forEach(modifier -> {
			if (modifier.getType() == type) {
				fetched.add(modifier);
			}
		});
		return fetched;
	}

	public PlacementModifier fetchFirst(PlacementModifierType<?> type) {
		return this.fetch(type).get(0);
	}

	public void add(PlacementModifier... modifiers) {
		this.add(List.of(modifiers));
	}

	public void add(List<PlacementModifier> modifiers) {
		this.placementModifiers.addAll(modifiers);
	}

	public void remove(PlacementModifierType<?> type) {
		for (int i = 0; i < this.placementModifiers.size(); i++) {
			if (this.placementModifiers.get(i).getType() == type) {
				this.placementModifiers.remove(i);
				i--;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <P extends PlacementModifier> void mutateTypeTo(PlacementModifierType<P> type, Function<P, PlacementModifier> mutator) {
		P modifier = (P) this.fetchFirst(type);
		PlacementModifier mutated = mutator.apply(modifier);
		this.remove(type);
		this.add(mutated);
	}

	public List<PlacementModifier> retrieve() {
		return this.placementModifiers;
	}
}
