package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.client.util.ColorUtil;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class Colors {

    public static abstract class ColorFormat {

        private ColorComponent a = this.createAComponent();
        private ColorComponent b = this.createBComponent();
        private ColorComponent c = this.createCComponent();

        private ColorFormat(int a, int b, int c) {
            this.a.setValue(a);
            this.b.setValue(b);
            this.c.setValue(c);
        }

        protected abstract ColorComponent createAComponent();

        protected abstract ColorComponent createBComponent();

        protected abstract ColorComponent createCComponent();

        protected int getA() {
            return this.a.getValue();
        }

        protected int getB() {
            return this.b.getValue();
        }

        protected int getC() {
            return this.c.getValue();
        }

        protected int setA(int a) {
            return this.a.setValue(a);
        }

        protected int setB(int b) {
            return this.b.setValue(b);
        }

        protected int setC(int c) {
            return this.c.setValue(c);
        }

        public static class ColorComponent {

            private final int minValue;
            private final int maxValue;

            private int value;

            public ColorComponent(int minValue, int maxValue) {
                this.minValue = minValue;
                this.maxValue = maxValue;
            }

            public int getValue() {
                return this.value;
            }

            public int setValue(int value) {
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
            return this.getA();
        }

        public int getGreen() {
            return this.getB();
        }

        public int getBlue() {
            return this.getC();
        }

        public int setRed(int red) {
            return this.setA(red);
        }

        public int setGreen(int green) {
            return this.setB(green);
        }

        public int setBlue(int blue) {
            return this.setC(blue);
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

        public Color toJavaColor() {
            return new Color(this.getRed(), this.getGreen(), this.getBlue());
        }

        public int toDecimal() {
            return ColorUtil.ARGB32.getArgb(255, this.getRed(), this.getGreen(), this.getBlue());
        }
    }

    public static class ARGB extends RGB {

        private ColorComponent alpha = new ColorComponent(0, 255);

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
            return this.alpha.getValue();
        }

        public int setAlpha(int alpha) {
            return this.alpha.setValue(alpha);
        }

        public int alterAlpha(int alteration) {
            return this.setAlpha(this.getAlpha() + alteration);
        }

        public Color toJavaColor() {
            return new Color(this.getAlpha(), this.getRed(), this.getGreen(), this.getBlue());
        }

        public int toDecimal() {
            return ColorUtil.ARGB32.getArgb(this.getAlpha(), this.getRed(), this.getGreen(), this.getBlue());
        }
    }
}
