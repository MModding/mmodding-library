package com.mmodding.library.datagen.api.provider;

import com.google.gson.JsonObject;
import com.mmodding.library.datagen.impl.provider.DataProviderExtension;
import com.mmodding.library.datagen.mixin.FabricLanguageProviderAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

/**
 * A variant of {@link FabricLanguageProvider} that does not overwrite previously written translation entries.
 */
public abstract class MModdingLanguageProvider extends FabricLanguageProvider {

	private final CompletableFuture<HolderLookup.Provider> future;

	protected MModdingLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future) {
		super(dataOutput, future);
		this.future = future;
	}

	protected MModdingLanguageProvider(FabricPackOutput dataOutput, String languageCode, CompletableFuture<HolderLookup.Provider> future) {
		super(dataOutput, languageCode, future);
		this.future = future;
	}

	@Override
	@SuppressWarnings("NonExtendableApiUsage")
	public CompletableFuture<?> run(CachedOutput writer) {
		TreeMap<String, String> translationEntries = new TreeMap<>();

		return this.future.thenCompose(provider -> {
			generateTranslations(provider, new TranslationBuilder() {
				public boolean has(String translationKey) {
					Objects.requireNonNull(translationKey, "translationKey");
					return translationEntries.containsKey(translationKey);
				}

				public @Nullable String overwrite(String translationKey, String value) {
					Objects.requireNonNull(translationKey, "translationKey");
					Objects.requireNonNull(value, "value");
					return translationEntries.put(translationKey, value);
				}
			});

			JsonObject langEntryJson = new JsonObject();

			for (Map.Entry<String, String> entry : translationEntries.entrySet()) {
				langEntryJson.addProperty(entry.getKey(), entry.getValue());
			}

			FabricLanguageProviderAccessor accessor = (FabricLanguageProviderAccessor) this;

			return DataProviderExtension.readAndWriteToPath(writer, langEntryJson, accessor.mmodding$getLangFilePath(accessor.mmodding$getLanguageCode()));
		});
	}
}
