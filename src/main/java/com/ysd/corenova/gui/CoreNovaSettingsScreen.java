package com.ysd.corenova.gui;

import com.ysd.corenova.CoreNovaMod;
import com.ysd.corenova.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CoreNovaSettingsScreen extends Screen {
    private static final int CARD_WIDTH = 320;
    private static final int CARD_HEIGHT = 120;
    private final Screen parent;
    private ModConfig.ConfigData config;
    private int scrollY = 0;
    private List<SettingCard> cards = new ArrayList<>();

    public CoreNovaSettingsScreen(Screen parent) {
        super(Text.literal("CoreNova 高级设置"));
        this.parent = parent;
        this.config = ModConfig.get();
    }

    @Override
    protected void init() {
        super.init();
        int startX = (width - CARD_WIDTH) / 2;
        int y = 40;
        
        // 渲染优化分类
        cards.add(new SettingCard(startX, y, CARD_WIDTH, CARD_HEIGHT, "渲染优化", List.of(
            new SliderOption("最大视距", config.maxRenderDistance, 16, 512, val -> config.maxRenderDistance = val),
            new ToggleOption("实体剔除", config.enableEntityCulling, val -> config.enableEntityCulling = val),
            new ToggleOption("遮挡剔除", config.enableOcclusionCulling, val -> config.enableOcclusionCulling = val)
        )));
        y += CARD_HEIGHT + 10;
        
        cards.add(new SettingCard(startX, y, CARD_WIDTH, CARD_HEIGHT, "AI 优化", List.of(
            new ToggleOption("AI 降频", config.enableAiThrottling, val -> config.enableAiThrottling = val),
            new SliderOption("降频距离(区块)", config.aiThrottleDistance, 16, 128, val -> config.aiThrottleDistance = val),
            new SliderOption("降频倍数", config.aiTickReduction, 2, 10, val -> config.aiTickReduction = val)
        )));
        
        // 底部按钮
        addDrawableChild(ButtonWidget.builder(Text.literal("完成"), button -> {
            ModConfig.save();
            client.setScreen(parent);
        }).dimensions(width / 2 - 100, height - 30, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 暗色半透明背景
        context.fill(0, 0, width, height, 0xCC000000);
        // 标题
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xFFFFFF);
        // 滚动区域（简易实现，你可以增加滚动逻辑）
        for (SettingCard card : cards) {
            card.render(context, mouseX, mouseY, delta);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    // 内部类：卡片
    static class SettingCard {
        private final int x, y, width, height;
        private final String title;
        private final List<SettingEntry> entries;
        
        SettingCard(int x, int y, int width, int height, String title, List<SettingEntry> entries) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.title = title;
            this.entries = entries;
        }
        
        void render(DrawContext context, int mouseX, int mouseY, float delta) {
            // 背景
            context.fill(x, y, x + width, y + height, 0xAA2A2A2A);
            context.drawBorder(x, y, width, height, 0xFFAAAAAA);
            context.drawText(MinecraftClient.getInstance().textRenderer, title, x + 10, y + 10, 0xFFFFFF, false);
            // 这里绘制条目占位（完整实现需要逐个绘制控件）
        }
    }
    
    interface SettingEntry { }
    record SliderOption(String name, int value, int min, int max, java.util.function.Consumer<Integer> setter) implements SettingEntry { }
    record ToggleOption(String name, boolean value, java.util.function.Consumer<Boolean> setter) implements SettingEntry { }
}
