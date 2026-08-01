package net.eliotex.zirconium.mixin.stbitextureloading;

import java.awt.image.BufferedImage;

import net.minecraft.client.render.texture.TextureAtlasSprite;
import net.minecraft.client.resource.metadata.AnimationMetadata;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Taken straight from Hodgepodge!!
@Mixin(TextureAtlasSprite.class)
public class TextureAtlasSpriteMixin {

    @Inject(method = "load", at = @At("TAIL"))
    void cleanupAfterLoad(BufferedImage[] images, AnimationMetadata aniData,
        CallbackInfo info) {
        for (BufferedImage img : images) {
            // Close any NativeBackedImage instances
            if (img instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) img).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}