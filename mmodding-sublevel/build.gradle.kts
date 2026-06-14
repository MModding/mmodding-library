plugins {
	id("mmodding.common-base")
}

mmodding {
	loom {
		accessWidenerPath = file("src/main/resources/mmodding_sublevel.classtweaker")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
	configureFabricModJson {
		accessWidener = "mmodding_sublevel.classtweaker"
		addMixin("mmodding_sublevel.mixins.json")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.sublevel.test.SublevelTests")
		}
	}
}
