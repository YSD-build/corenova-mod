package com.ysd.corenova.mixin;

import com.ysd.corenova.config.ModConfig;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoOptionsScreen.class)
public class VideoSettingsScreenMixin {
    @ModifyArg(
        method = "addOptions",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/option/SimpleOption;)V"
        ),
        index = 0
    )
    private SimpleOption<?>[] modifyRenderDistance(SimpleOption<?>[] options) {
        for (int i = 0; i < options.length; i++) {
            if (options[i] == GameOptions.RENDER_DISTANCE) {
                // 修改视距范围
                options[i] = new SimpleOption<>(
                    "options.renderDistance",
                    SimpleOption.constantValidator(Integer.valueOf(2)),
                    (value) -> value + " chunks",
                    (value) -> {},
                    SimpleOption::call,
                    (SimpleOption<Integer>) options[i],
                    ModConfig.get().maxRenderDistance
                );
                break;
            }
        }
        return options;
    }
}
