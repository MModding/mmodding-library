package com.mmodding.library.core.api.registry;

import net.minecraft.util.Identifier;

public class IdentifierUtil {

	/**
	 * Returns a new {@link Identifier} following this pattern: `[namespace]:[path]_[extension]`.
	 * @param identifier the original identifier
	 * @param extension the path extension
	 * @return the newly created identifier
	 */
	public static Identifier extend(Identifier identifier, String extension) {
		return IdentifierUtil.extend(identifier, extension, false);
	}

	/**
	 * Returns a new {@link Identifier} following this pattern: `[namespace]:[extension]_[path]`.
	 * @param identifier the original identifier
	 * @param extension the path extension
	 * @return the newly created identifier
	 */
	public static Identifier extend(String extension, Identifier identifier) {
		return IdentifierUtil.extend(identifier, extension, true);
	}

	/**
	 * Returns a new {@link Identifier}, being a modified version of an original one with its path being extended.
	 * @param identifier the original identifier
	 * @param extension the path extension
	 * @param before where to put the path extension
	 * @return the newly created identifier
	 */
	public static Identifier extend(Identifier identifier, String extension, boolean before) {
		return IdentifierUtil.extend(identifier, extension, before, '_');
	}

	/**
	 * Returns a new {@link Identifier}, being a modified version of an original one with its path being extended by a
	 * specific separator character.
	 * @param identifier the original identifier
	 * @param extension the path extension
	 * @param before where to put the path extension
	 * @param separator the separator character
	 * @return the newly created identifier
	 */
	public static Identifier extend(Identifier identifier, String extension, boolean before, char separator) {
		return !extension.isEmpty() ? new Identifier(
			identifier.getNamespace(),
			!before ? identifier.getPath() + separator + extension : extension + separator + identifier.getPath()
		) : identifier;
	}
}
