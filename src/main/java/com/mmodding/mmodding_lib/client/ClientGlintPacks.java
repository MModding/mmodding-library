package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.client.glint.GlintPack;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class ClientGlintPacks {

	public static void register() {
		GlintPack.create(MModdingLib.createId("textures/glint_pack/white.png")).register(new MModdingIdentifier("white"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/orange.png")).register(new MModdingIdentifier("orange"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/magenta.png")).register(new MModdingIdentifier("magenta"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/light_blue.png")).register(new MModdingIdentifier("light_blue"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/yellow.png")).register(new MModdingIdentifier("yellow"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/lime.png")).register(new MModdingIdentifier("lime"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/pink.png")).register(new MModdingIdentifier("pink"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/gray.png")).register(new MModdingIdentifier("gray"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/light_gray.png")).register(new MModdingIdentifier("light_gray"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/cyan.png")).register(new MModdingIdentifier("cyan"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/purple.png")).register(new MModdingIdentifier("purple"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/blue.png")).register(new MModdingIdentifier("blue"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/brown.png")).register(new MModdingIdentifier("brown"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/green.png")).register(new MModdingIdentifier("green"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/red.png")).register(new MModdingIdentifier("red"));
		GlintPack.create(MModdingLib.createId("textures/glint_pack/black.png")).register(new MModdingIdentifier("black"));

		GlintPack.create(MModdingLib.createId("textures/glint_pack/blank.png")).register(new MModdingIdentifier("blank"));
	}
}
