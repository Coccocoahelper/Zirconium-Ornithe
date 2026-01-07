package net.eliotex.zirconium.mixin.details;

import net.eliotex.zirconium.config.ZirconiumConfig;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.world.World;
import net.minecraft.client.render.world.WorldRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(
        method = "renderSky",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderSky(float tickDelta, int anaglyphRenderPass, CallbackInfo ci) {
        if (!ZirconiumConfig.instance.sky.get()) {
            ci.cancel();
        }
    }

    @Inject(
        method = "renderLightSky",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderLightSky(CallbackInfo ci) {
        if (!ZirconiumConfig.instance.sky.get()) {
            ci.cancel();
        }
    }

    @Inject(
        method = "renderDarkSky",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderDarkSky(CallbackInfo ci) {
        if (!ZirconiumConfig.instance.sky.get()) {
            ci.cancel();
        }
    }

    @Inject(
        method = "renderEndSky",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderEndSky(CallbackInfo ci) {
        if (!ZirconiumConfig.instance.sky.get()) {
            ci.cancel();
        }
    }

    @Inject(
        method = "renderStars",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderStars(CallbackInfo ci) {
        if (!ZirconiumConfig.instance.stars.get()) {
            ci.cancel();
        }
    }
}