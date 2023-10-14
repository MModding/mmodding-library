package com.mmodding.mmodding_lib.library.texts;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.component.LiteralComponent;
import net.minecraft.text.component.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class Prefix extends TemplatedText<Prefix> {

	private final boolean spaced;

	private Prefix(TextComponent component, List<Text> siblings, Style style, boolean spaced) {
		super(component, siblings, style);
		this.spaced = spaced;
	}

	public static Prefix spaced(String text) {
		return new Prefix(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, true);
	}

	public static Prefix of(String text) {
		return new Prefix(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, false);
	}

	public static Prefix empty() {
		return new Prefix(TextComponent.EMPTY, new ArrayList<>(), Style.EMPTY, false);
	}

    public boolean isSpaced() {
		return this.spaced;
	}

	@Override
	public Prefix copyContentOnly() {
		return new Prefix(this.asComponent(), new ArrayList<>(), Style.EMPTY, this.isSpaced());
	}

	@Override
	public Prefix copy() {
		return new Prefix(this.asComponent(), this.getSiblings(), this.getStyle(), this.isSpaced());
	}
}
