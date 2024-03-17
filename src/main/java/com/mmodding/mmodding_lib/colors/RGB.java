package com.mmodding.mmodding_lib.colors;

import net.minecraft.client.util.ColorUtil;

public class RGB implements Color {

	protected int red;
	protected int green;
	protected int blue;

	RGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return this.red;
	}

	public int getGreen() {
		return this.green;
	}

	public int getBlue() {
		return this.blue;
	}

	public int setRed(int red) {
		return this.red = red;
	}

	public int setGreen(int green) {
		return this.green = green;
	}

	public int setBlue(int blue) {
		return this.blue = blue;
	}

	public int alterRed(int alteration) {
		return this.setRed(this.getRed() + alteration);
	}

	public int alterGreen(int alteration) {
		return this.setGreen(this.getGreen() + alteration);
	}

	public int alterBlue(int alteration) {
		return this.setBlue(this.getBlue() + alteration);
	}

	public ARGB toARGB(int alpha) {
		return new ARGB(alpha, this.red, this.green, this.blue);
	}

	public HSB toHSB() {
		float[] hsb = java.awt.Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null);
		return new HSB(hsb[0], hsb[1], hsb[2]);
	}

	@Override
	public int toDecimal() {
		return ColorUtil.ARGB32.getArgb(255, this.red, this.green, this.blue);
	}

	@Override
	public java.awt.Color toJavaColor() {
		return new java.awt.Color(this.red, this.green, this.blue);
	}
}
