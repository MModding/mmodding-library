package com.mmodding.mmodding_lib.library.client.render.model.json;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.*;
import java.util.function.Function;

/**
 * A no-pixel-collision version of the {@link ItemModelGenerator}.
 * <br>
 * Builtin Model Identifier is <code>mmodding:builtin/not_colliding_generated</code>.
 * <br>
 * Item Model Identifier is <code>mmodding:item/not_colliding_generated`</code>.
 */
@ClientOnly
public class NotCollidingItemModelGenerator extends ItemModelGenerator {

	public static final NotCollidingItemModelGenerator INSTANCE = new NotCollidingItemModelGenerator();

	public static final JsonUnbakedModel MARKER = Util.make(
		JsonUnbakedModel.deserialize("{\"gui_light\": \"front\"}"),
		blockModel -> blockModel.id = "not colliding item model generation marker"
	);

	@ApiStatus.Internal
	public final Map<Integer, List<Frame>> frames = new HashMap<>();

	@Override
	public JsonUnbakedModel create(Function<SpriteIdentifier, Sprite> textureGetter, JsonUnbakedModel blockModel) {
		Map<String, Either<SpriteIdentifier, String>> map = new HashMap<>();
		List<ModelElement> list = new ArrayList<>();

		// Pre-Collects Frames
		// We collect the frames of each layer earlier in order to filter the colliding pixels.
		// Usage of super#getFrames in super#addSubComponents is then wrapped in a mixin.
		for (int i = 0; i < LAYERS.size(); i++) {
			String layer = LAYERS.get(i);
			SpriteIdentifier spriteIdentifier = blockModel.resolveSprite(layer);
			Sprite sprite = textureGetter.apply(spriteIdentifier);
			this.frames.put(i, super.getFrames(sprite));
		}

		// Removes Colliding Pixels
		// Works by picking layers from the top to the bottom, and for each of them it removes all frames of all layers
		// that are lower than the current one and that are at the same position as frames of the current one.
		for (int i = LAYERS.size() - 1; i >= 0; i--) {
			List<Frame> layer = this.frames.get(i);
			for (int j = 1; j < i - 1; j++) {
				List<Frame> current = this.frames.get(i - j);
				for (Frame frame : layer) {
					current.removeIf(
						currentFrame -> frame.getMin() == currentFrame.getMin()
							&& frame.getMax() == currentFrame.getMax()
							&& frame.getLevel() == currentFrame.getLevel()
					);
				}
				this.frames.put(i - j, current);
			}
			this.frames.put(i, layer);
		}

		for (int i = 0; i < LAYERS.size(); i++) {
			String layer = LAYERS.get(i);
			if (!blockModel.textureExists(layer)) {
				break;
			}
			SpriteIdentifier spriteIdentifier = blockModel.resolveSprite(layer);
			map.put(layer, Either.left(spriteIdentifier));
			Sprite sprite = textureGetter.apply(spriteIdentifier);
			List<ModelElement> newLayer = this.addLayerElements(i, layer, sprite);
			list.addAll(newLayer);
		}

		map.put("particle", blockModel.textureExists("particle") ? Either.left(blockModel.resolveSprite("particle")) : map.get("layer0"));
		JsonUnbakedModel jsonUnbakedModel = new JsonUnbakedModel(
			null, list, map, false, blockModel.getGuiLight(), blockModel.getTransformations(), blockModel.getOverrides()
		);
		jsonUnbakedModel.id = blockModel.id;

		return jsonUnbakedModel;
	}

	// Prevents Frame Expansion
	// That is less optimized, I guess, but it allows easier filtering of the frames.
	@Override
	protected void createOrExpandCurrentCube(List<Frame> cubes, Side side, int x, int y) {
		int j = side.isVertical() ? y : x;
		int k = side.isVertical() ? x : y;
		cubes.add(new Frame(side, k, j));
	}
}
