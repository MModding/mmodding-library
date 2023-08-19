package com.mmodding.mmodding_lib.library.stellar;

import com.mmodding.mmodding_lib.ducks.ClientStellarStatusDuckInterface;
import com.mmodding.mmodding_lib.library.client.utils.RenderLayerUtils;
import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.TextureLocation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class StellarObject {

	private final Identifier stellarCycle;
	private final TextureLocation textureLocation;
	private final float width;
	private final float height;
    private final float u;
    private final float v;

    public StellarObject(Identifier stellarCycle, TextureLocation textureLocation, float width, float height, float u, float v) {
        this.stellarCycle = stellarCycle;
		this.textureLocation = textureLocation;
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
    }

	public void register() {
		RenderLayerUtils.addStellarObject(this);
	}

	public StellarCycle getCycle() {
		return MModdingGlobalMaps.getStellarCycle(this.stellarCycle);
	}

	public StellarStatus getStatus(ClientWorld world) {
		return ((ClientStellarStatusDuckInterface) world).mmodding_lib$getStellarStatus(this.stellarCycle);
	}

    public void render(MatrixStack matrices, ClientWorld world, float tickDelta) {
        matrices.push();

        float i = 1.0f - world.getRainGradient(tickDelta);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(this.getCycle().getXSkyAngle(this.getStatus(world).getCurrentTime()) * 360.0f));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(this.getCycle().getYSkyAngle(this.getStatus(world).getCurrentTime()) * 360.0f));

        Matrix4f matrix4f = matrices.peek().getModel();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.textureLocation);

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        float minU = 0;
        float minV = 0;
        float maxU = this.u;
        float maxV = this.v;

        bufferBuilder.vertex(matrix4f, -this.width, 100.0f, -this.height).uv(minU, maxV).next();
        bufferBuilder.vertex(matrix4f, this.width, 100.0f, -this.height).uv(maxU, maxV).next();
        bufferBuilder.vertex(matrix4f, this.width, 100.0f, this.height).uv(maxU, minV).next();
        bufferBuilder.vertex(matrix4f, -this.width, 100.0f, this.height).uv(minU, minV).next();

        BufferRenderer.draw(bufferBuilder.end());

        matrices.pop();
    }
}
