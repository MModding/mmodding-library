package com.mmodding.library.core.api.registry;

import net.minecraft.util.Identifier;

public class IdentifierUtil {

	public static Identifier extend(Identifier identifier, String extension) {
		return IdentifierUtil.extend(identifier, extension, false);
	}

	public static Identifier extend(String extension, Identifier identifier) {
		return IdentifierUtil.extend(identifier, extension, true);
	}

	public static Identifier extend(Identifier identifier, String extension, boolean before) {
		return IdentifierUtil.extend(identifier, extension, before, '_');
	}

	public static Identifier extend(Identifier identifier, String extension, boolean before, char separator) {
		return !extension.isEmpty() ? new Identifier(
			identifier.getNamespace(),
			!before ? identifier.getPath() + separator + extension : extension + separator + identifier.getPath()
		) : identifier;
	}
}
