package com.mmodding.mmodding_lib.colors;

import net.minecraft.util.math.MathHelper;

public class HSB implements Color {

	private float hue;

	private float saturation;

	private float brightness;

	HSB(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = this.safe(saturation);
		this.brightness = this.safe(brightness);
	}

	private float safe(float value) {
		return MathHelper.clamp(value, 0.0f, 1.0f);
	}

	public float getHue() {
		return this.hue;
	}

	public float getSaturation() {
		return this.saturation;
	}

	public float getBrightness() {
		return this.brightness;
	}

	public float setHue(float hue) {
		return this.hue = hue;
	}

	public float setSaturation(float saturation) {
		return this.saturation = this.safe(saturation);
	}

	public float setBrightness(float brightness) {
		return this.brightness = this.safe(brightness);
	}

	public float alterHue(float alteration) {
		return this.setHue(this.getHue() + alteration);
	}

	public float alterSaturation(float alteration) {
		return this.setSaturation(this.safe(this.getSaturation() + alteration));
	}

	public float alterBrightness(float alteration) {
		return this.setBrightness(this.safe(this.getBrightness() + alteration));
	}

	public RGB toRGB() {
		int rgb = java.awt.Color.HSBtoRGB(this.getHue(), this.getSaturation(), this.getBrightness());
		return Color.rgb(rgb);
	}

	@Override
	public int toDecimal() {
		return this.toRGB().toDecimal();
	}

	@Override
	public java.awt.Color toJavaColor() {
		return this.toRGB().toJavaColor();
	}
}
