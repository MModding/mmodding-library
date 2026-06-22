plugins {
	id("com.mmodding.library.module")
}

mmodding {
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		testmodImplementation("mmodding-block")
	}
	configureFabricModJson {
		addMixin("mmodding_energy.mixins.json")
		withEntrypoints {
			init("com.mmodding.library.energy.impl.MModdingEnergyInitializer")
		}
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.energy.test.EnergyTests")
			client("com.mmodding.library.energy.test.client.EnergyClientTests")
		}
	}
}
