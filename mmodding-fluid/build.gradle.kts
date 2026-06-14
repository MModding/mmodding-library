plugins {
	id("mmodding.common-base")
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
