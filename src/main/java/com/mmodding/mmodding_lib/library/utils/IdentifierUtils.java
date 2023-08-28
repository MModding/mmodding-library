package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Identifier;

import java.util.Objects;

public class IdentifierUtils {

	public static Identifier extend(Identifier identifier, String extension) {
		return !Objects.equals(extension, "") ? new Identifier(identifier.getNamespace(), identifier.getPath() + "_" + extension) : identifier;
	}
}
