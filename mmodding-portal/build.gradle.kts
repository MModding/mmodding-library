plugins {
	id("com.mmodding.library.module")
}

loom.accessWidenerPath = file("src/main/resources/mmodding_portal.classtweaker")

mmodding {
	configureFabricModJson {
		accessWidener = "mmodding_portal.classtweaker"
		addMixin("mmodding_portal.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-math")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.portal.test.MModdingPortalTests")
		}
	}
}
