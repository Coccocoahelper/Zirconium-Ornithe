package net.eliotex.zirconium.mixin.stbitextureloading;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.render.texture.TextureAtlas;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.eliotex.zirconium.utils.misc.NativeBackedImage;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin {

    @Redirect(
        method = "loadAndStitch",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/texture/TextureUtil;readImage(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;",
            remap = false))
    private BufferedImage redirectImageRead(InputStream stream) {
        try {
            return NativeBackedImage.make(stream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}