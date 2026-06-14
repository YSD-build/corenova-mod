package com.ysd.corenova.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysd.corenova.CoreNovaMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("corenova.json");
    private static ConfigData data;

    public static class ConfigData {
        public int maxRenderDistance = 512;
        public boolean enableEntityCulling = true;
        public int entityCullingDistance = 128;
        public boolean enableAiThrottling = true;
        public int aiThrottleDistance = 64;
        public int aiTickReduction = 4;
        public boolean enableOcclusionCulling = true;
        public boolean verboseLogging = false;
    }

    static {
        data = new ConfigData();
    }

    public static void load() {
        if (CONFIG_PATH.toFile().exists()) {
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                CoreNovaMod.LOGGER.error("Failed to load config", e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            CoreNovaMod.LOGGER.error("Failed to save config", e);
        }
    }

    public static ConfigData get() { return data; }
}
