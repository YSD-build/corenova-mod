package com.ysd.corenova;

import com.ysd.corenova.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreNovaMod implements ModInitializer {
    public static final String MOD_ID = "corenova";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.load();
        LOGGER.info("CoreNova Mod initialized! Render distance limit: {}", ModConfig.get().maxRenderDistance);
    }
}
