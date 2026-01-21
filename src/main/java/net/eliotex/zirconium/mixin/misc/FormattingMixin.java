package net.eliotex.zirconium.mixin.misc;

import net.minecraft.text.Formatting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.eliotex.zirconium.utils.StringUtils;

@Mixin(Formatting.class)
public class FormattingMixin {

    /**
     * @author Alexdoru
     * @reason It's faster
     */
    @Overwrite
    public static String strip(String s) {
        return StringUtils.strip(s);
    }
}