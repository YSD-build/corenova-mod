package com.ysd.corenova;

import com.ysd.corenova.gui.CoreNovaSettingsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.OptionsScreen;

public class CoreNovaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof OptionsScreen) {
                // 在原版选项屏幕添加一个按钮，打开我们的设置界面
                // 具体实现在 OptionsScreenMixin 中通过 mixin 添加按钮更简单，这里不再重复。
            }
        });
    }
}
