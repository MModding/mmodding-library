package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class ClientGlintPacks {

	public static void register() {
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/white")).register(new MModdingIdentifier("white"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/orange")).register(new MModdingIdentifier("orange"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/magenta")).register(new MModdingIdentifier("magenta"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/light_blue")).register(new MModdingIdentifier("light_blue"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/yellow")).register(new MModdingIdentifier("yellow"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lime")).register(new MModdingIdentifier("lime"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/pink")).register(new MModdingIdentifier("pink"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/gray")).register(new MModdingIdentifier("gray"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/light_gray")).register(new MModdingIdentifier("light_gray"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/cyan")).register(new MModdingIdentifier("cyan"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/purple")).register(new MModdingIdentifier("purple"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/blue")).register(new MModdingIdentifier("blue"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/brown")).register(new MModdingIdentifier("brown"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/green")).register(new MModdingIdentifier("green"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/red")).register(new MModdingIdentifier("red"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/black")).register(new MModdingIdentifier("black"));

		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/white")).register(new MModdingIdentifier("lightened_white"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/orange")).register(new MModdingIdentifier("lightened_orange"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/magenta")).register(new MModdingIdentifier("lightened_magenta"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/light_blue")).register(new MModdingIdentifier("lightened_light_blue"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/yellow")).register(new MModdingIdentifier("lightened_yellow"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/lime")).register(new MModdingIdentifier("lightened_lime"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/pink")).register(new MModdingIdentifier("lightened_pink"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/gray")).register(new MModdingIdentifier("lightened_gray"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/light_gray")).register(new MModdingIdentifier("lightened_light_gray"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/cyan")).register(new MModdingIdentifier("lightened_cyan"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/purple")).register(new MModdingIdentifier("lightened_purple"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/blue")).register(new MModdingIdentifier("lightened_blue"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/brown")).register(new MModdingIdentifier("lightened_brown"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/green")).register(new MModdingIdentifier("lightened_green"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/red")).register(new MModdingIdentifier("lightened_red"));
		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/lightened/black")).register(new MModdingIdentifier("lightened_black"));

		GlintPack.create(MModdingLib.getTextureLocation("glint_pack/blank")).register(new MModdingIdentifier("blank"));
	}
}
