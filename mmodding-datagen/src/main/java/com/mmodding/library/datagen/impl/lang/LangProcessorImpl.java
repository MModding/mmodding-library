package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LangProcessorImpl<T> implements LangProcessor<T> {

	@Override
	public String process(RegistryKey<T> key) {
		String path = key.getValue().getPath();
		String[] words = path.split("_");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
			if (i != words.length - 1) {
				builder.append(" ");
			}
		}
		builder.delete(builder.toString().length() - 1, builder.toString().length() - 1);
		return builder.toString();
	}
}
