package dev.serenity;

import dev.serenity.module.ModuleManager;
import dev.serenity.ui.hud.HUD;

public class Serenity {
    private static final Serenity instance = new Serenity();
    private final String name = "Serenity";
    private final double version = 1.0;
    private final ModuleManager moduleManager = new ModuleManager();
    private final HUD hud = new HUD();

    public void startClient() {

    }

    public void stopClient() {

    }

    public static Serenity getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public double getVersion() {
        return version;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public HUD getHud() {
        return hud;
    }
}
