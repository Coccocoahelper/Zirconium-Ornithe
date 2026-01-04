package net.eliotex.zirconium.mixin.fontbatching;

import net.eliotex.zirconium.utils.font.BatchingTextRenderer;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.resource.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextRenderer.class)
public interface TextRendererAccessor {

    int angelica$drawStringBatched(String text, int x, int y, int argb, boolean dropShadow);

    BatchingTextRenderer angelica$getBatcher();

    void angelica$bindTexture(Identifier location);
}