plugins {
	id("com.mmodding.library.module")
}

loom.accessWidenerPath = file("src/main/resources/mmodding_woodset.classtweaker")

mmodding {
	configureFabricModJson {
		accessWidener = "mmodding_woodset.classtweaker"
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-item")
		implementation("mmodding-block")
	}
}
