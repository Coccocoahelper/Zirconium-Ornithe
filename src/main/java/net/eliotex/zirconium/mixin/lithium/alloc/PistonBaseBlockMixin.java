package net.eliotex.zirconium.mixin.lithium.alloc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.PistonBaseBlock;
import net.minecraft.util.math.Direction;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin {

    private static final Direction[] DIRECTIONS = Direction.values();

    @Redirect(method = "shouldExtend", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Direction;values()[Lnet/minecraft/util/math/Direction;"))
    private Direction[] redirectShouldExtendDirectionValues() {
        return DIRECTIONS;
    }

}