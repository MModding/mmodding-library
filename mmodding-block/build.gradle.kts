plugins {
	id("com.mmodding.library.module")
}

mmodding {
	loom.accessWidenerPath = file("src/main/resources/mmodding_block.classtweaker")
	configureFabricModJson {
		accessWidener = "mmodding_block.classtweaker"
		addMixin("mmodding_block.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-math")
		implementation("mmodding-item")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.block.test.BlockTests")
		}
	}
}
