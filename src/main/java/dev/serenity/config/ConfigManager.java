package dev.serenity.config;

import dev.serenity.Serenity;
import dev.serenity.utilities.MinecraftInstance;

import java.io.File;

public class ConfigManager extends MinecraftInstance {
    public static final File dir = new File(mc.mcDataDir, Serenity.getInstance().getName());
    public static final File configsDir = new File(dir, "configs");
    public static final Config config = new Config("default.json");

    public static void init() {
        if(!dir.exists()) {
            dir.mkdir();
        }
        if(!configsDir.exists()) {
            configsDir.mkdir();
        }
    }

    public static void stop() {
        config.saveConfig();
    }
}
