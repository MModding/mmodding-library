package com.mmodding.library.energy.impl;

import com.mmodding.library.energy.impl.block.BlockEnergySavedData;
import com.mmodding.library.energy.impl.item.ItemEnergyImpl;
import net.fabricmc.api.ModInitializer;

public class MModdingEnergyInitializer implements ModInitializer {

	@Override
	public void onInitialize() {
		ItemEnergyImpl.classload();
		BlockEnergySavedData.registerCacheInvalidation();
	}
}
