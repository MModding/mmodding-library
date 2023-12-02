package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.network.request.c2s.ServerRequestHandler;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkIdentifier;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.item.ItemStack;

public class RequestHandlers {

	public static void register() {
		ServerRequestHandler.create(new MModdingIdentifier("glint_pack"), arguments -> {
			ItemStack stack = arguments.getItemStack(0);
			if (GlintPackView.of(stack) != null) {
				return NetworkIdentifier.of(GlintPackView.of(stack).getGlintPack(stack));
			}
			else {
				return NetworkIdentifier.of(new MModdingIdentifier("empty"));
			}
		});
	}
}
