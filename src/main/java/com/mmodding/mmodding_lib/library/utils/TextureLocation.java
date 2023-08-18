package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Identifier;

public class TextureLocation extends Identifier {

	public TextureLocation(String path) {
		this(path, true);
	}

	public TextureLocation(String namespace, String path) {
		this(namespace, path, true);
	}

	protected TextureLocation(String path, boolean withPath) {
		this("minecraft", path, withPath);
	}

	protected TextureLocation(String namespace, String path, boolean withPath) {
		super(namespace, (withPath ? "textures/" : "") + path + ".png");
	}

	public static TextureLocation withoutPath(String path) {
		return new TextureLocation(path, false);
	}

	public static TextureLocation withoutPath(String namespace, String path) {
		return new TextureLocation(namespace, path, false);
	}

	public static class Block extends TextureLocation {

		public Block(String path) {
			this("minecraft", path);
		}

		public Block(String namespace, String path) {
			super(namespace, "block/" + path);
		}
	}

	public static class Item extends TextureLocation {

		public Item(String path) {
			this("minecraft", path);
		}

		public Item(String namespace, String path) {
			super(namespace, "item/" + path);
		}
	}
}
