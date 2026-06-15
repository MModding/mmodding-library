plugins {
	id("com.mmodding.library.module")
}

loom.accessWidenerPath = file("src/main/resources/mmodding_core.classtweaker")

mmodding {
	configureFabricModJson {
		accessWidener = "mmodding_core.classtweaker"
		addMixin("mmodding_core.mixins.json")
		withEntrypoints {
			init("com.mmodding.library.core.impl.MModdingInitializer")
		}
	}
	modules {
		implementation("mmodding-java")
	}
}
