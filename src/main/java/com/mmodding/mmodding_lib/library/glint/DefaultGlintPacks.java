package com.mmodding.mmodding_lib.library.glint;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;

public class DefaultGlintPacks {

    public static final GlintPackView WHITE = DefaultGlintPacks.create("white");
    public static final GlintPackView ORANGE = DefaultGlintPacks.create("orange");
    public static final GlintPackView MAGENTA = DefaultGlintPacks.create("magenta");
    public static final GlintPackView LIGHT_BLUE = DefaultGlintPacks.create("light_blue");
    public static final GlintPackView YELLOW = DefaultGlintPacks.create("yellow");
    public static final GlintPackView LIME = DefaultGlintPacks.create("lime");
    public static final GlintPackView PINK = DefaultGlintPacks.create("pink");
    public static final GlintPackView GRAY = DefaultGlintPacks.create("gray");
    public static final GlintPackView LIGHT_GRAY = DefaultGlintPacks.create("light_gray");
    public static final GlintPackView CYAN = DefaultGlintPacks.create("cyan");
    public static final GlintPackView PURPLE = DefaultGlintPacks.create("purple");
    public static final GlintPackView BLUE = DefaultGlintPacks.create("blue");
    public static final GlintPackView BROWN = DefaultGlintPacks.create("brown");
    public static final GlintPackView GREEN = DefaultGlintPacks.create("green");
    public static final GlintPackView RED = DefaultGlintPacks.create("red");
    public static final GlintPackView BLACK = DefaultGlintPacks.create("black");

	public static final GlintPackView LIGHTENED_WHITE = DefaultGlintPacks.create("lightened_white");
	public static final GlintPackView LIGHTENED_ORANGE = DefaultGlintPacks.create("lightened_orange");
	public static final GlintPackView LIGHTENED_MAGENTA = DefaultGlintPacks.create("lightened_magenta");
	public static final GlintPackView LIGHTENED_LIGHT_BLUE = DefaultGlintPacks.create("lightened_light_blue");
	public static final GlintPackView LIGHTENED_YELLOW = DefaultGlintPacks.create("lightened_yellow");
	public static final GlintPackView LIGHTENED_LIME = DefaultGlintPacks.create("lightened_lime");
	public static final GlintPackView LIGHTENED_PINK = DefaultGlintPacks.create("lightened_pink");
	public static final GlintPackView LIGHTENED_GRAY = DefaultGlintPacks.create("lightened_gray");
	public static final GlintPackView LIGHTENED_LIGHT_GRAY = DefaultGlintPacks.create("lightened_light_gray");
	public static final GlintPackView LIGHTENED_CYAN = DefaultGlintPacks.create("lightened_cyan");
	public static final GlintPackView LIGHTENED_PURPLE = DefaultGlintPacks.create("lightened_purple");
	public static final GlintPackView LIGHTENED_BLUE = DefaultGlintPacks.create("lightened_blue");
	public static final GlintPackView LIGHTENED_BROWN = DefaultGlintPacks.create("lightened_brown");
	public static final GlintPackView LIGHTENED_GREEN = DefaultGlintPacks.create("lightened_green");
	public static final GlintPackView LIGHTENED_RED = DefaultGlintPacks.create("lightened_red");
	public static final GlintPackView LIGHTENED_BLACK = DefaultGlintPacks.create("lightened_black");

	public static final GlintPackView BLANK = DefaultGlintPacks.create("blank");

	private static GlintPackView create(String path) {
        return new SimpleGlintPackView(new MModdingIdentifier(path));
	}
}
