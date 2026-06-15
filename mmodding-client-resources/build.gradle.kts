import com.mmodding.gradle.api.EnvironmentTarget

plugins {
	id("com.mmodding.library.module")
}

loom.accessWidenerPath = file("src/main/resources/mmodding_client_resources.classtweaker")

mmodding {
	configureFabricModJson {
		environment = EnvironmentTarget.CLIENT
		accessWidener = "mmodding_client_resources.classtweaker"
		addMixin("mmodding_client_resources.mixins.json")
		withEntrypoints {
			client("com.mmodding.library.resource.impl.client.MModdingClientResourceSetup")
		}
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.resource.test.client.RenderingTests")
			client("com.mmodding.library.resource.test.client.RenderingTestsClient")
		}
	}
}
