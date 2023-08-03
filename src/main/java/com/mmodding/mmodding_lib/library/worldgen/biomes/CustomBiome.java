package com.mmodding.mmodding_lib.library.worldgen.biomes;

import net.minecraft.world.biome.Biome;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class CustomBiome implements BiomeRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final Supplier<Biome> biome;

    public CustomBiome(Supplier<Biome> biome) {
        this.biome = biome;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
		this.registered.set(true);
    }

	public Biome getBiome() {
		return this.biome.get();
	}
}
