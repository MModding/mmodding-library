package com.mmodding.library.worldgen.api.feature.replication;

import net.minecraft.world.gen.decorator.PlacementModifierType;
import net.minecraft.world.gen.feature.PlacementModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class PlacementModifiers extends ArrayList<PlacementModifier> implements List<PlacementModifier> {

	public PlacementModifiers(@NotNull Collection<? extends PlacementModifier> c) {
		super(c);
	}

	public List<PlacementModifier> fetch(PlacementModifierType<?> type) {
		List<PlacementModifier> fetched = new ArrayList<>();
		this.forEach(modifier -> {
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
		this.addAll(modifiers);
	}

	public void remove(PlacementModifierType<?> type) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).getType() == type) {
				this.remove(i);
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
}
