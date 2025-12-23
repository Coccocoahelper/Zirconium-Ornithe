package net.eliotex.zirconium.mixin.languagereloading;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   targets = {"net/minecraft/client/gui/screen/options/LanguageOptionsScreen$LanguageSelectionListWidget"}
)
public class LanguageOptionsScreenMixin {
   @Redirect(
      method = {"selectEntry"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;reloadResources()V"
)
   )
   private void onlyReloadLanguage(Minecraft client) {
      client.getLanguageManager().reload(client.getResourceManager());
   }
}