package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.glint.DefaultGlintPacks;
import com.mmodding.mmodding_lib.library.client.utils.RenderLayerUtils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.function.Predicate;

@ClientOnly
@ApiStatus.Internal
public class ClientGlintPackOverrides {

    public static void register() {

        Predicate<Item> canApply = item -> MModdingLibClient.LIBRARY_CLIENT_CONFIG.getContent().getBoolean("applyGlintPackOverridesToVanillaItems");

        RenderLayerUtils.addGlintPackOverride(Items.TURTLE_HELMET, DefaultGlintPacks.GREEN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.WOODEN_SWORD, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.WOODEN_PICKAXE, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.WOODEN_AXE, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.WOODEN_SHOVEL, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.WOODEN_HOE, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.LEATHER_HELMET, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.LEATHER_CHESTPLATE, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.LEATHER_LEGGINGS, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.LEATHER_BOOTS, DefaultGlintPacks.BROWN, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.STONE_SWORD, DefaultGlintPacks.GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.STONE_PICKAXE, DefaultGlintPacks.GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.STONE_AXE, DefaultGlintPacks.GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.STONE_SHOVEL, DefaultGlintPacks.GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.STONE_HOE, DefaultGlintPacks.GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.CHAINMAIL_HELMET, DefaultGlintPacks.LIGHT_GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.CHAINMAIL_CHESTPLATE, DefaultGlintPacks.LIGHT_GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.CHAINMAIL_LEGGINGS, DefaultGlintPacks.LIGHT_GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.CHAINMAIL_BOOTS, DefaultGlintPacks.LIGHT_GRAY, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_SWORD, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_PICKAXE, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_AXE, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_SHOVEL, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_HOE, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_HELMET, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_CHESTPLATE, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_LEGGINGS, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.IRON_BOOTS, DefaultGlintPacks.WHITE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_SWORD, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_PICKAXE, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_AXE, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_SHOVEL, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_HOE, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_HELMET, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_CHESTPLATE, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_LEGGINGS, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.GOLDEN_BOOTS, DefaultGlintPacks.YELLOW, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_SWORD, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_PICKAXE, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_AXE, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_SHOVEL, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_HOE, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_HELMET, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_CHESTPLATE, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_LEGGINGS, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.DIAMOND_BOOTS, DefaultGlintPacks.LIGHT_BLUE, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_SWORD, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_PICKAXE, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_AXE, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_SHOVEL, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_HOE, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_HELMET, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_CHESTPLATE, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_LEGGINGS, DefaultGlintPacks.BLACK, canApply);
        RenderLayerUtils.addGlintPackOverride(Items.NETHERITE_BOOTS, DefaultGlintPacks.BLACK, canApply);
    }
}
