package net.eliotex.zirconium.utils.font;

import net.minecraft.resource.Identifier;

public interface TextRendererAccessor {

    int angelica$drawStringBatched(String text, float x, float y, int color, boolean shadow);

    BatchingTextRenderer angelica$getBatcher();

    void angelica$bindTexture(Identifier location);

}