package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Util;

public class ConfigUtils {

	public static String getSeparator() {
		return Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS ? "\\" : "/";
	}
}
