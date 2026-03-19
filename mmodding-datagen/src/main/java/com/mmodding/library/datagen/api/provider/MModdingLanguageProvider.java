package com.mmodding.library.datagen.api.provider;

import com.google.gson.JsonObject;
import com.mmodding.library.datagen.impl.provider.DataProviderExtension;
import com.mmodding.library.datagen.mixin.FabricLanguageProviderAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.data.DataWriter;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

/**
 * A variant of {@link FabricLanguageProvider} that does not overwrite previously written translation entries.
 */
public abstract class MModdingLanguageProvider extends FabricLanguageProvider {

	protected MModdingLanguageProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	protected MModdingLanguageProvider(FabricDataOutput dataOutput, String languageCode) {
		super(dataOutput, languageCode);
	}

	@Override
	public CompletableFuture<?> run(DataWriter writer) {
		TreeMap<String, String> translationEntries = new TreeMap<>();

		generateTranslations((String key, String value) -> {
			Objects.requireNonNull(key);
			Objects.requireNonNull(value);

			if (translationEntries.containsKey(key)) {
				throw new RuntimeException("Existing translation key found - " + key + " - Duplicate will be ignored.");
			}

			translationEntries.put(key, value);
		});

		JsonObject langEntryJson = new JsonObject();

		for (Map.Entry<String, String> entry : translationEntries.entrySet()) {
			langEntryJson.addProperty(entry.getKey(), entry.getValue());
		}

		FabricLanguageProviderAccessor accessor = (FabricLanguageProviderAccessor) this;

		return DataProviderExtension.readAndWriteToPath(writer, langEntryJson, accessor.mmodding$getLangFilePath(accessor.mmodding$getLanguageCode()));
	}
}
