package net.eliotex.zirconium.mixin.languagereloading;

import net.minecraft.client.Minecraft;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Credits to Mixces for this optimization!
@Mixin(
   targets = {"net/minecraft/client/gui/screen/options/LanguageOptionsScreen$LanguageSelectionListWidget"}
)
public class LanguageSelectionListWidgetMixin {
   @WrapOperation(
      method = {"entryClicked"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;reloadResources()V"
)}
   )
   private void onlyReloadLanguage(Minecraft client, Operation<Void> original) {
      client.getLanguageManager().reload(client.getResourceManager());
   }

}
