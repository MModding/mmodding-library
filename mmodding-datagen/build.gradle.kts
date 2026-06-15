plugins {
	id("com.mmodding.library.module")
}

mmodding {
	loom.accessWidenerPath = file("src/main/resources/mmodding_datagen.classtweaker")
	configureFabricModJson {
		accessWidener = "mmodding_datagen.classtweaker"
		addMixin("mmodding_datagen.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-block")
		implementation("mmodding-item")
		implementation("mmodding-woodset")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.datagen.test.DatagenTests")
			custom("fabric-datagen", "com.mmodding.library.datagen.test.DatagenTests")
		}
	}
}
