package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Identifier;

public class TextureLocation extends Identifier {

	public TextureLocation(String path) {
		this("minecraft", path);
	}

	public TextureLocation(String namespace, String path) {
		super(namespace,  "textures/" + path + ".png");
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
