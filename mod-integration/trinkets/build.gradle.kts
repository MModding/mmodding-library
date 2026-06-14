import com.mmodding.gradle.api.EnvironmentTarget

plugins {
	id("mmodding.common-base")
}

repositories {
	maven {
		name = "Nucleoid"
		url = uri("https://maven.nucleoid.xyz/releases")
	}
}

mmodding {
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
		implementation("mmodding-item")
		implementation("mmodding-client-resources")
	}
	configureFabricModJson {
		withDependencies {
			addDependency("trinkets_updated", ">=" + libs.versions.trinkets.updated.get())
		}
		addMixin("mmodding_integration_trinkets.mixins.json")
	}
	configureTestmod {
		environment = EnvironmentTarget.CLIENT
		withEntrypoints {
			init("com.mmodding.library.integration.trinkets.test.IntegrationTests")
			client("com.mmodding.library.integration.trinkets.test.IntegrationTestsClient")
		}
	}
}

dependencies {
	implementation(libs.trinkets)
}
