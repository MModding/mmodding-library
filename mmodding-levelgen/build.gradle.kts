plugins {
	id("com.mmodding.library.module")
}

mmodding {
	loom {
		accessWidenerPath = file("src/main/resources/mmodding_levelgen.classtweaker")
	}
	configureFabricModJson {
		accessWidener = "mmodding_levelgen.classtweaker"
		addMixin("mmodding_levelgen.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-block")
		implementation("mmodding-java")
	}
	configureFabricModJson {
		withEntrypoints {
			init("com.mmodding.library.levelgen.impl.MModdingLevelgenInitializer")
		}
	}
}
