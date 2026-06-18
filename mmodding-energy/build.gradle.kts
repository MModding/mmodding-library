plugins {
	id("com.mmodding.library.module")
}

mmodding {
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
	configureFabricModJson {
		withEntrypoints {
			init("com.mmodding.library.energy.impl.MModdingEnergyInitializer")
		}
	}
}
