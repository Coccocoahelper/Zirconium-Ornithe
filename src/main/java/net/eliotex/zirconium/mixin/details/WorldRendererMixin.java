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

/**
 * Controls cloud rendering
 * Cloud height is controlled via MixinWorldProvider
 */
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    /**
     * Control cloud rendering
     */
    @Inject(
        method = "renderClouds",
        at = @At("HEAD"),
        cancellable = true
    )
    private void renderClouds(float tickDelta, int anaglyphRenderPass, CallbackInfo ci) {
        if (!ZirconiumConfig.instance.clouds.get()) {
            ci.cancel();
        }
    }

    /**
     * Control sky rendering
     * In 1.12.2, sky rendering is in renderSky method
     */
    @Inject(
        method = "renderSky(FI)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void renderSky(float tickDelta, int anaglyphRenderPass, CallbackInfo ci) {
        if (!ZirconiumConfig.instance.sky.get()) {
            ci.cancel();
        }
    }

    /**
     * Control star rendering by wrapping the star brightness calculation
     * In 1.12.2, stars are rendered based on getStarBrightness
     */
    @WrapOperation(
        method = "renderSky(FI)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getStarBrightness(F)F"
        )
    )
    private float wrapGetStarBrightness(World world, float tickDelta, Operation<Float> original) {
        // 0.0f = no rendering
        return ZirconiumConfig.instance.stars.get() ? original.call(world, tickDelta) : 0.0f;
    }

    /**
     * Control sun rendering by wrapping the bindTexture call for sun texture
     * In 1.12.2, the sun texture path is "textures/environment/sun.png"
     */
    @WrapOperation(
        method = "renderSky(FI)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/world/WorldRenderer;renderSky(Lcom/mojang/blaze3d/vertex/BufferBuilder;FFZ)V",
            ordinal = 0
        )
    )
    private void wrapRenderSun(WorldRenderer instance, BufferBuilder buffer, float r, float g, float b, Operation<Void> original) {
        if (ZirconiumConfig.instance.sun.get()) {
            original.call(instance, buffer, r, g, b);
        }
    }

    /**
     * Control moon rendering by wrapping the bindTexture call for moon texture
     * In 1.12.2, the moon texture path is "textures/environment/moon_phases.png"
     */
    @WrapOperation(
        method = "renderSky(FI)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/world/WorldRenderer;renderSky(Lcom/mojang/blaze3d/vertex/BufferBuilder;FFZ)V",
            ordinal = 1 
        )
    )
    private void wrapRenderMoon(WorldRenderer instance, BufferBuilder buffer, float r, float g, float b, Operation<Void> original) {
        if (ZirconiumConfig.instance.moon.get()) {
            original.call(instance, buffer, r, g, b);
        }
    }
}