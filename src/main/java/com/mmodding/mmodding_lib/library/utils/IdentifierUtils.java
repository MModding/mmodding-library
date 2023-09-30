package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Identifier;

import java.util.Objects;

public class IdentifierUtils {

	public static Identifier extend(Identifier identifier, String extension) {
		return IdentifierUtils.extend(identifier, extension, false);
	}

	public static Identifier extend(String extension, Identifier identifier) {
		return IdentifierUtils.extend(identifier, extension, true);
	}

	public static Identifier extend(Identifier identifier, String extension, boolean before) {
		return IdentifierUtils.extend(identifier, extension, before, '_');
	}

	public static Identifier extend(Identifier identifier, String extension, boolean before, char separator) {
		return !Objects.equals(extension, "") ? new Identifier(
			identifier.getNamespace(),
			!before ? identifier.getPath() + separator + extension : extension + separator + identifier.getPath()
		) : identifier;
	}
}
