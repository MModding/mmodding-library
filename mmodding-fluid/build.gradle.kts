plugins {
	id("com.mmodding.library.module")
}

mmodding {
    loom.accessWidenerPath = file("src/main/resources/mmodding_fluid.classtweaker")
	configureFabricModJson {
		addMixin("mmodding_fluid.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
}
