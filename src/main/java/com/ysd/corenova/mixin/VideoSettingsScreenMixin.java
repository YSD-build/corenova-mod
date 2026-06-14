package com.ysd.corenova.mixin;

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
                // 获取原滑块的值范围，将最大值改为 512
                SimpleOption<Integer> original = (SimpleOption<Integer>) options[i];
                options[i] = new SimpleOption<>(
                    original.getKey(),
                    original.getValidator(),
                    (value) -> value + " chunks",
                    (value) -> {},
                    original.valueGetter(),
                    original.valueSetter(),
                    512  // 新最大值
                );
                break;
            }
        }
        return options;
    }
}
