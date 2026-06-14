plugins {
	id("mmodding.common-base")
}

mmodding {
	configureFabricModJson {
		withEntrypoints {
			init("com.mmodding.library.config.impl.ConfigInitializer")
			client("com.mmodding.library.config.impl.client.ConfigClientInitializer")
		}
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-network")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.config.test.ConfigTests")
		}
	}
}
