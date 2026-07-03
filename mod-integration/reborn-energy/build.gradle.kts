plugins {
	id("com.mmodding.library.integration")
}

mmodding {
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-energy")
	}
	configureFabricModJson {
		withDependencies {
			addDependency("team_reborn_energy", ">=" + libs.versions.reborn.energy.get())
		}
		withEntrypoints {
			init("com.mmodding.library.integration.reborn_energy.RebornEnergyIntegration")
		}
	}
}

dependencies {
	implementation(libs.reborn.energy)
}
