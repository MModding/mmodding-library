package com.mmodding.library.java.api.color;

public class ARGB extends RGB implements Color {

	private int alpha;

	ARGB(int alpha, int red, int green, int blue) {
		super(red, green, blue);
		this.alpha = this.safe(alpha);
	}

	public int getAlpha() {
		return this.alpha;
	}

	public int setAlpha(int alpha) {
		return this.alpha = this.safe(alpha);
	}

	public int alterAlpha(int alteration) {
		return this.setAlpha(this.safe(this.getAlpha() + alteration));
	}

	public RGB toRGB() {
		return new RGB(this.red, this.green, this.blue);
	}

	@Override
	public int toDecimal() {
		return this.alpha << 24 | this.red << 16 | this.green << 8 | blue;
	}

	@Override
	public java.awt.Color toJavaColor() {
		return new java.awt.Color(this.alpha, this.red, this.green, this.blue);
	}

	@Override
	public ARGB copy() {
		return new ARGB(this.alpha, this.red, this.green, this.blue);
	}
}
