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
		withEntrypoints {
			init("com.mmodding.library.energy.impl.MModdingEnergyInitializer")
		}
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.energy.test.EnergyTests")
		}
	}
}
