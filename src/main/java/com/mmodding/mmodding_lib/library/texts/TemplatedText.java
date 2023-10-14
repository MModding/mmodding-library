package com.mmodding.mmodding_lib.library.texts;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.component.TextComponent;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class TemplatedText<T extends TemplatedText<T>> extends MutableText {

	protected TemplatedText(TextComponent component, List<Text> siblings, Style style) {
		super(component, siblings, style);
	}

	public MutableText asMutable() {
		return Text.empty().append(this);
	}

	@Override
	public T setStyle(Style style) {
		super.setStyle(style);
		return (T) this;
	}

	@Override
	public T append(String text) {
		super.append(text);
		return (T) this;
	}

	@Override
	public T append(Text text) {
		super.append(text);
		return (T) this;
	}

	@Override
	public T styled(UnaryOperator<Style> styleUpdater) {
		super.styled(styleUpdater);
		return (T) this;
	}

	@Override
	public T fillStyle(Style styleOverride) {
		super.fillStyle(styleOverride);
		return (T) this;
	}

	@Override
	public T formatted(Formatting... formattings) {
		super.formatted(formattings);
		return (T) this;
	}

	@Override
	public T formatted(Formatting formatting) {
		super.formatted(formatting);
		return (T) this;
	}
}
