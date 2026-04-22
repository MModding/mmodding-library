package com.mmodding.library.rendering.api.sprite;

import com.mmodding.library.rendering.impl.sprite.TextureAliasesImpl;
import net.minecraft.resources.Identifier;

public class TextureAliases {

	/**
	 * Creates a texture alias for a specified texture location.
	 * @param alias the alias location
	 * @param target the texture location
	 * @apiNote Beware! An alias will prevent its location to be considered, as it will always be redirected.
	 */
	public static void create(Identifier alias, Identifier target) {
		TextureAliasesImpl.create(alias, target);
	}
}
