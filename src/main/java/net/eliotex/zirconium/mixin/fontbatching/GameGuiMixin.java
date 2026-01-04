package net.eliotex.zirconium.mixin.fontbatching;

import net.eliotex.zirconium.utils.font.TextRendererAccessor;
import net.minecraft.client.gui.GameGui;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public abstract class GameGuiMixin {
    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Inject(
            method = "render", at = @At("HEAD")
    )
    private void begin(float tickDelta, CallbackInfo ci) {
        ((TextRendererAccessor)getTextRenderer()).angelica$getBatcher().beginBatch();
    }

    @Inject(
            method = "render", at = @At("TAIL")
    )
    private void end(float tickDelta, CallbackInfo ci) {
        ((TextRendererAccessor)getTextRenderer()).angelica$getBatcher().endBatch();
    }
}
