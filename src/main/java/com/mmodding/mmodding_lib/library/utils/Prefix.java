package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.component.LiteralComponent;
import net.minecraft.text.component.TextComponent;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Prefix extends MutableText {

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

	public MutableText asMutable() {
		return Text.empty().append(this);
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

	@Override
	public Prefix setStyle(Style style) {
		super.setStyle(style);
		return this;
	}

	@Override
	public Prefix append(String text) {
		super.append(text);
		return this;
	}

	@Override
	public Prefix append(Text text) {
		super.append(text);
		return this;
	}

	@Override
	public Prefix styled(UnaryOperator<Style> styleUpdater) {
		super.styled(styleUpdater);
		return this;
	}

	@Override
	public Prefix fillStyle(Style styleOverride) {
		super.fillStyle(styleOverride);
		return this;
	}

	@Override
	public Prefix formatted(Formatting... formattings) {
		super.formatted(formattings);
		return this;
	}

	@Override
	public Prefix formatted(Formatting formatting) {
		super.formatted(formatting);
		return this;
	}
}
