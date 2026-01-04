package net.eliotex.zirconium.mixin.fontbatching;

import net.eliotex.zirconium.utils.font.BatchingTextRenderer;
import net.eliotex.zirconium.utils.font.TextRendererAccessor;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.texture.TextureManager;
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
 *
 * @author eigenraven
 */
@Mixin(TextRenderer.class)
public abstract class TextRendererMixin implements TextRendererAccessor {
    @Shadow
    private int[] colors;

    // Alpha
    @Shadow
    private float a;

    // Red
    @Shadow
    private float r;

    // Green
    @Shadow
    private float g;

    // Blue
    @Shadow
    private float b;

    @Shadow
    private int[] characterWidths;

    @Shadow
    private boolean unicode;

    @Shadow
    private float x;

    @Shadow
    private float y;

    @Shadow
    @Final
    private static Identifier[] UNICODE_PAGE_LOCATIONS;
    @Shadow
    private byte[] glyphSizes;
    @Shadow
    @Final
    private Identifier fontLocation;
    @Shadow
    private boolean bidirectional;

    @Shadow
    protected abstract String bidirectionalShaping(String text);

    @Shadow
    @Final
    private TextureManager textureManager;
    @Unique
    public BatchingTextRenderer angelica$batcher;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void angelica$injectBatcher(GameOptions settings, Identifier fontLocation, TextureManager texManager,
                                        boolean unicodeMode, CallbackInfo ci) {
        angelica$batcher = new BatchingTextRenderer((TextRenderer) (Object) this, UNICODE_PAGE_LOCATIONS, this.characterWidths, this.glyphSizes, this.colors, this.fontLocation);
    }

    /**
     * Only allow using the batched renderer if we are not in an OpenGL Display List
     * Batched font renderer is not compatible with display lists, and won't really
     * help performance when display lists are already being used anyway.
     */
    @Inject(method = "draw(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    public void angelica$BatchedTextRendererDrawString(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        if (GL11.glGetInteger(GL11.GL_LIST_MODE) == 0) {
            cir.setReturnValue(angelica$drawStringBatched(text, x, y, color, shadow));
        }
    }

    /**
     * See the above explanation about batched renderer in display lists.
     */
    @Inject(method = "drawLayer(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    public void angelica$BatchedTextRendererRenderString(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        if (GL11.glGetInteger(GL11.GL_LIST_MODE) == 0) {
            cir.setReturnValue(angelica$drawStringBatched(text, x, y, color, shadow));
        }
    }

    @Override
    public int angelica$drawStringBatched(String text, float x, float y, int color, boolean shadow) {
        if (text == null) {
            return 0;
        } else {
            if (this.bidirectional) {
                text = this.bidirectionalShaping(text);
            }

            if ((color & 0xfc000000) == 0) {
                color |= 0xff000000;
            }

            this.r = (float) (color >> 16 & 255) / 255.0F;
            this.g = (float) (color >> 8 & 255) / 255.0F;
            this.b = (float) (color & 255) / 255.0F;
            this.a = (float) (color >> 24 & 255) / 255.0F;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.x = x;
            this.y = y;
            return (int) angelica$batcher.drawString(x, y, color, shadow, unicode, text, 0, text.length());
        }
    }

    @Override
    public BatchingTextRenderer angelica$getBatcher() {
        return angelica$batcher;
    }

    @Override
    public void angelica$bindTexture(Identifier location) {
        textureManager.bind(location);
    }

}