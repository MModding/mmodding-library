package com.mmodding.library.datagen.api.lang;

public class DefaultLangProcessors {

	/**
	 * For example: "quiche_lorraine" -> "Quiche Lorraine"
	 */
	public static final TranslationProcessor CLASSIC = identifier -> {
		String path = identifier.getPath();
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
	};

	/**
	 * For example: "diamond_block" -> "Block of Diamond"
	 */
	public static final TranslationProcessor ORE = identifier -> {
		String classic = CLASSIC.process(identifier);
		return "Block of " + classic.substring(0, classic.length() - 5);
	};

    /**
	 * For example: "acacia_chest_boat" -> "Acacia Boat with Chest"
     */
	public static final TranslationProcessor CHEST_BOAT = identifier -> {
		String classic = CLASSIC.process(identifier);
		return classic.substring(0, classic.length() - 11) + " Boat with Chest";
	};
}
