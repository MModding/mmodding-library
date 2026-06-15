plugins {
	id("com.mmodding.library.module")
}

mmodding {
	configureFabricModJson {
		addMixin("mmodding_fluid.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
}
