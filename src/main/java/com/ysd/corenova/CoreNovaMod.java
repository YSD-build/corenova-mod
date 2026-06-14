package com.ysd.corenova;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreNovaMod implements ModInitializer {
    public static final String MOD_ID = "corenova";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("CoreNova Mod initialized!");
    }
}
