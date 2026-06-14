plugins {
	id("mmodding.common-base")
}

loom.accessWidenerPath = file("src/main/resources/mmodding_item.classtweaker")

mmodding {
	configureFabricModJson {
		accessWidener = "mmodding_item.classtweaker"
		addMixin("mmodding_item.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
}
