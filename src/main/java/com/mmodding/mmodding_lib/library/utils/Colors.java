package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.client.util.ColorUtil;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class Colors {

    public static abstract class ColorFormat {

        private final ColorComponent a = this.createAComponent();
        private final ColorComponent b = this.createBComponent();
        private final ColorComponent c = this.createCComponent();

        private ColorFormat(float a, float b, float c) {
            this.a.setValue(a);
            this.b.setValue(b);
            this.c.setValue(c);
        }

        protected abstract ColorComponent createAComponent();

        protected abstract ColorComponent createBComponent();

        protected abstract ColorComponent createCComponent();

        protected float getA() {
            return this.a.getValue();
        }

        protected float getB() {
            return this.b.getValue();
        }

        protected float getC() {
            return this.c.getValue();
        }

        protected float setA(float a) {
            return this.a.setValue(a);
        }

        protected float setB(float b) {
            return this.b.setValue(b);
        }

        protected float setC(float c) {
            return this.c.setValue(c);
        }

        public static class ColorComponent {

            private final float minValue;
            private final float maxValue;

            private float value;

            public ColorComponent(float minValue, float maxValue) {
                this.minValue = minValue;
                this.maxValue = maxValue;
            }

            public float getValue() {
                return this.value;
            }

            public float setValue(float value) {
                return this.value = MathHelper.clamp(value, this.minValue, this.maxValue);
            }
        }
    }

    public static class RGB extends ColorFormat {

        @Override
        protected ColorComponent createAComponent() {
            return new ColorComponent(0, 255);
        }

        @Override
        protected ColorComponent createBComponent() {
            return new ColorComponent(0, 255);
        }

        @Override
        protected ColorComponent createCComponent() {
            return new ColorComponent(0, 255);
        }

        public RGB(int red, int green, int blue) {
            super(red, green, blue);
        }

        public static RGB fromDecimal(int decimal) {
            return new RGB(
                ColorUtil.ARGB32.getRed(decimal),
                ColorUtil.ARGB32.getGreen(decimal),
                ColorUtil.ARGB32.getBlue(decimal)
            );
        }

        public static RGB fromJavaColor(Color color) {
            return new RGB(color.getRed(), color.getGreen(), color.getBlue());
        }

        public int getRed() {
            return (int) this.getA();
        }

        public int getGreen() {
            return (int) this.getB();
        }

        public int getBlue() {
            return (int) this.getC();
        }

        public int setRed(int red) {
            return (int) this.setA(red);
        }

        public int setGreen(int green) {
            return (int) this.setB(green);
        }

        public int setBlue(int blue) {
            return (int) this.setC(blue);
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
            return new ARGB(alpha, this);
        }

		public HSB toHSB() {
			float[] hsb = Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null);
			return new HSB(hsb[0], hsb[1], hsb[2]);
		}

        public int toDecimal() {
            return ColorUtil.ARGB32.getArgb(255, this.getRed(), this.getGreen(), this.getBlue());
        }

        public Color toJavaColor() {
            return new Color(this.getRed(), this.getGreen(), this.getBlue());
        }
    }

    public static class ARGB extends RGB {

        private final ColorComponent alpha = new ColorComponent(0, 255);

        public ARGB(int alpha, RGB rgb) {
            this(alpha, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
        }

        public ARGB(int alpha, int red, int green, int blue) {
            super(red, green, blue);
            this.alpha.setValue(alpha);
        }

        public static ARGB fromDecimal(int decimal) {
            return new ARGB(
                ColorUtil.ARGB32.getAlpha(decimal),
                RGB.fromDecimal(decimal)
            );
        }

        public static ARGB fromJavaColor(Color color) {
            return new ARGB(color.getAlpha(), RGB.fromJavaColor(color));
        }

        public int getAlpha() {
            return (int) this.alpha.getValue();
        }

        public int setAlpha(int alpha) {
            return (int) this.alpha.setValue(alpha);
        }

        public int alterAlpha(int alteration) {
            return this.setAlpha(this.getAlpha() + alteration);
        }

        @Override
        public int toDecimal() {
            return ColorUtil.ARGB32.getArgb(this.getAlpha(), this.getRed(), this.getGreen(), this.getBlue());
        }

        @Override
        public Color toJavaColor() {
            return new Color(this.getAlpha(), this.getRed(), this.getGreen(), this.getBlue());
        }
    }

	public static class HSB extends ColorFormat {

		@Override
		protected ColorComponent createAComponent() {
			return new ColorComponent(0, 360);
		}

		@Override
		protected ColorComponent createBComponent() {
			return new ColorComponent(0, 100);
		}

		@Override
		protected ColorComponent createCComponent() {
			return new ColorComponent(0, 100);
		}

		private HSB(float hue, float saturation, float brightness) {
			super(hue, saturation, brightness);
		}

		public static HSB fromDecimal(int decimal) {
			return RGB.fromDecimal(decimal).toHSB();
		}

		public static HSB fromJavaColor(Color color) {
			return RGB.fromJavaColor(color).toHSB();
		}

		public float getHue() {
			return this.getA();
		}

		public float getSaturation() {
			return this.getB();
		}

		public float getBrightness() {
			return this.getC();
		}

		public float setHue(float hue) {
			return this.setA(hue);
		}

		public float setSaturation(float saturation) {
			return this.setB(saturation);
		}

		public float setBrightness(float brightness) {
			return this.setC(brightness);
		}

		public float alterHue(float alteration) {
			return this.setHue(this.getHue() + alteration);
		}

		public float alterSaturation(float alteration) {
			return this.setSaturation(this.getSaturation() + alteration);
		}

		public float alterBrightness(float alteration) {
			return this.setBrightness(this.getBrightness() + alteration);
		}

		public RGB toRGB() {
			int rgb = Color.HSBtoRGB(this.getHue(), this.getSaturation(), this.getBrightness());
			return RGB.fromDecimal(rgb);
		}

		public int toDecimal() {
			return this.toRGB().toDecimal();
		}

		public Color toJavaColor() {
			return this.toRGB().toJavaColor();
		}
	}
}
