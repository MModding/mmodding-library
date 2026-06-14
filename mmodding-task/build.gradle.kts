plugins {
	id("mmodding.common-base")
}

mmodding {
	configureFabricModJson {
		addMixin("mmodding_task.mixins.json")
	}
	modules {
		implementation("mmodding-core")
		implementation("mmodding-java")
	}
	configureTestmod {
		withEntrypoints {
			init("com.mmodding.library.task.test.TaskTests")
		}
	}
}
