plugins {
	id("com.mmodding.library.module")
}

mmodding {
	configureFabricModJson {
		addMixin("mmodding_enchantment.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-item")
	}
}
