package net.eliotex.zirconium.mixin.languagereloading;

import net.minecraft.client.Minecraft;
import net.minecaft.client.gui.screen.options.LanguageOptionsScreen.LanguageSelectionListWidget;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({LanguageSelectionListWidget.class})
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