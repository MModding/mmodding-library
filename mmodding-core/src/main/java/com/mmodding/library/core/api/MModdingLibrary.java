package com.mmodding.library.core.api;

import com.mmodding.library.core.impl.MModdingInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface MModdingLibrary {

	static Map<String, ElementsManager> getAllManagers() {
		return MModdingInitializer.MANAGERS;
	}

	static Identifier createId(String path) {
		return new Identifier("mmodding", path);
	}
}
