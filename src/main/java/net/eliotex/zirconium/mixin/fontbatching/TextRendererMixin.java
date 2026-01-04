package net.eliotex.zirconium.mixin.fontbatching;

import net.eliotex.zirconium.utils.font.BatchingTextRenderer;
import net.eliotex.zirconium.mixin.fontbatching.TextRendererAccessor;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.client.options.GameOptions;
import net.minecraft.resource.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the horrible performance of TextRenderer
 * @author eigenraven
 */
@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer implements TextRendererAccessor {
    @Shadow
    private int[] colorCode;

    @Shadow
    private float alpha;

    @Shadow
    private float red;

    /** Actually green */
    @Shadow
    private float blue;

    /** Actually blue */
    @Shadow
    private float green;

    @Shadow
    protected int[] charWidth;

    @Shadow
    private boolean unicodeFlag;

    @Shadow
    protected float posX;

    @Shadow
    protected float posY;

    @Shadow
    @Final
    private static Identifier[] unicodePageLocations;
    @Shadow
    protected byte[] glyphWidth;
    @Shadow
    @Final
    protected Identifier locationFontTexture;
    @Shadow
    private boolean bidiFlag;

    @Shadow
    protected abstract String bidiReorder(String p_147647_1_);

    @Shadow(remap = false)
    protected abstract void bindTexture(Identifier location);

    @Unique
    public BatchingTextRenderer angelica$batcher;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void angelica$injectBatcher(GameOptions settings, Identifier fontLocation, TextureManager texManager,
                                        boolean unicodeMode, CallbackInfo ci) {
        angelica$batcher = new BatchingTextRenderer((TextRenderer) (Object) this, unicodePageLocations, this.charWidth, this.glyphWidth, this.colorCode, this.locationFontTexture);
    }

    /**
     * Only allow using the batched renderer if we are not in an OpenGL Display List
     * Batched font renderer is not compatible with display lists, and won't really
     * help performance when display lists are already being used anyway.
     */
    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    public void angelica$BatchedTextRendererDrawString(String text, float x, float y, int argb, boolean dropShadow, CallbackInfoReturnable<Integer> cir)
    {
        if (GL11.glGetInteger(GL11.GL_LIST_MODE) == 0) {
            cir.setReturnValue(angelica$drawStringBatched(text, x, y, argb, dropShadow));
        }
    }

    /**
     * See the above explanation about batched renderer in display lists.
     */
    @Inject(method = "renderString", at = @At("HEAD"), cancellable = true)
    public void angelica$BatchedTextRendererRenderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> cir) {
        if (GL11.glGetInteger(GL11.GL_LIST_MODE) == 0) {
            cir.setReturnValue(angelica$drawStringBatched(text, x, y, color, dropShadow));
        }
    }

    @Override
    public int angelica$drawStringBatched(String text, float x, float y, int argb, boolean dropShadow) {
        if (text == null)
        {
            return 0;
        }
        else
        {
            if (this.bidiFlag)
            {
                text = this.bidiReorder(text);
            }

            if ((argb & 0xfc000000) == 0)
            {
                argb |= 0xff000000;
            }

            this.red = (float)(argb >> 16 & 255) / 255.0F;
            this.blue = (float)(argb >> 8 & 255) / 255.0F;
            this.green = (float)(argb & 255) / 255.0F;
            this.alpha = (float)(argb >> 24 & 255) / 255.0F;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.posX = x;
            this.posY = y;
            return (int) angelica$batcher.drawString(x, y, argb, dropShadow, unicodeFlag, text, 0, text.length());
        }
    }

    @Override
    public BatchingTextRenderer angelica$getBatcher() {
        return angelica$batcher;
    }

    @Override
    public void angelica$bindTexture(Identifier location) { this.bindTexture(location); }

}