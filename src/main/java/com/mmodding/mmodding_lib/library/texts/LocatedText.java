package com.mmodding.mmodding_lib.library.texts;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.component.LiteralComponent;
import net.minecraft.text.component.TextComponent;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;

public class LocatedText extends TemplatedText<LocatedText> {

	private final float x;
	private final float y;
	private final float z;

	private LocatedText(TextComponent component, List<Text> siblings, Style style, float x, float y, float z) {
		super(component, siblings, style);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static LocatedText ofPos(String text, Vec3f position) {
		return new LocatedText(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, position.getX(), position.getY(), position.getZ());
	}

	public static LocatedText ofCoordinates(String text, float x, float y, float z) {
		return new LocatedText(new LiteralComponent(text), new ArrayList<>(), Style.EMPTY, x, y, z);
	}

	public static LocatedText empty() {
		return new LocatedText(LiteralComponent.EMPTY, new ArrayList<>(), Style.EMPTY, 0.0f, 0.0f, 0.0f);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

	@Override
	public LocatedText copyContentOnly() {
		return new LocatedText(this.asComponent(), new ArrayList<>(), Style.EMPTY, this.getX(), this.getY(), this.getZ());
	}

	@Override
	public LocatedText copy() {
		return new LocatedText(this.asComponent(), this.getSiblings(), this.getStyle(), this.getX(), this.getY(), this.getZ());
	}
}
