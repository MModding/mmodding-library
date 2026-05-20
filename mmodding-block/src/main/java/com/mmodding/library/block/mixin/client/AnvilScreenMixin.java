package com.mmodding.library.block.mixin.client;

import com.mmodding.library.block.api.catalog.AdvancedAnvilBlock;
import com.mmodding.library.block.mixin.ItemCombinerMenuAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {

	public AnvilScreenMixin(AnvilMenu menu, Inventory inventory, Component title, Identifier menuResource) {
		super(menu, inventory, title, menuResource);
	}

	@ModifyConstant(method = "extractLabels", constant = @Constant(intValue = 40))
	private int modifyExperienceLimit(int original) {
		Block instance = ((ItemCombinerMenuAccessor) this.getMenu()).mmodding$access().evaluate((level, pos) -> level.getBlockState(pos).getBlock(), Blocks.AIR);
		if (instance instanceof AdvancedAnvilBlock anvil) {
			return anvil.getExperienceLimit();
		}
		else {
			return original;
		}
	}
}
