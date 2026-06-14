package com.ysd.corenova.mixin;

import com.ysd.corenova.gui.CoreNovaSettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin {
    @Inject(method = "init", at = @At("RETURN"))
    private void addCoreNovaButton(CallbackInfo ci) {
        OptionsScreen screen = (OptionsScreen) (Object) this;
        screen.addDrawableChild(ButtonWidget.builder(
            Text.literal("CoreNova Settings"),
            button -> screen.client.setScreen(new CoreNovaSettingsScreen(screen))
        ).dimensions(screen.width / 2 - 100, screen.height / 6 + 144, 200, 20).build());
    }
}
