package com.mmodding.library.datagen.impl.lang;

import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import net.minecraft.resources.ResourceKey;

public class DefaultLangProcessorsImpl {

	public static class ClassicLangProcessorImpl<T> implements TranslationProcessor<T> {

		@Override
		public String process(ResourceKey<T> key) {
			String path = key.identifier().getPath();
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
}
