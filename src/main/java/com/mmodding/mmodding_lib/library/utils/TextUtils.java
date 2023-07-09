package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextUtils {

	public static Text spaceBetween(MutableText text, Text... siblings) {
		for (Text sibling : siblings) {
			text.append(" ").append(sibling);
		}
		return text;
	}
}
