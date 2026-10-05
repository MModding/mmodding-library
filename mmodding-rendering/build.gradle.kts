import com.mmodding.gradle.api.EnvironmentTarget

plugins {
	id("com.mmodding.library.module")
}

mmodding {
    configureFabricModJson {
        environment = EnvironmentTarget.CLIENT
        addMixin("mmodding_rendering.mixins.json")
        withEntrypoints {
            client("com.mmodding.library.rendering.impl.client.MModdingRenderingSetup")
        }
    }
    modules {
        implementation("mmodding-core")
        implementation("mmodding-java")
    }
    configureTestmod {
        withEntrypoints {
            client("com.mmodding.library.rendering.test.client.SpecialBlockTests")
        }
    }
}
