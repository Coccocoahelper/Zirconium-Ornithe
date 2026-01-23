package net.eliotex.zirconium.mixim.fasterglint;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.platform.GLX;
import net.minecraft.client.render.vertex.Tesselator;
import net.minecraft.client.render.entity.ItemRenderer;

@Mixin(value = ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow
	public float zLevel;

	/**
	 * @author jss2a98aj
	 * @reason Makes renderGlint faster and fixes glBlendFunc being left with the wrong values.
	 */
	@Overwrite
    private void renderEnchantmentGlint(int unused, int posX, int posY, int width, int height) {
        final float timeUVSpeed = 0.00390625F;
        final Tesselator tessellator = Tesselator.instance;
        final long time = Minecraft.getTime();

        float layerUVNoise = 4.0F;

        GLX.blendFuncSeparate(772, 1, 0, 0);

        GLX.blendFuncSeparate(772, 1, 0, 1);

        //for(int layer = 0; layer < 2; ++layer) {
            final int timeUVDenominator = 3000 /*+ layer * 1873*/;
            final float timeUVNoise = (float)(time % (long)timeUVDenominator) / (float)timeUVDenominator * 256F;

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(
                 posX, (posY + height), zLevel,
                 ((timeUVNoise + (float)height * layerUVNoise) * timeUVSpeed), ((float)height * timeUVSpeed)
            );
            tessellator.addVertexWithUV(
                (posX + width), (posY + height), zLevel,
                ((timeUVNoise + (float)width + (float)height * layerUVNoise) * timeUVSpeed), ((float)height * timeUVSpeed)
            );
            tessellator.addVertexWithUV(
                 (posX + width), posY, zLevel,
                 ((timeUVNoise + (float)width) * timeUVSpeed), 0D
            );
            tessellator.addVertexWithUV(
                 posX, posY, zLevel,
                 (timeUVNoise * timeUVSpeed), 0D
            );
            tessellator.draw();

        //layerUVNoise = -1.0F;
        //}

        GLX.blendFuncSeparate(770, 771, 1, 0);
    }
}