package dev.serenity;

import dev.serenity.command.CommandManager;
import dev.serenity.config.ConfigManager;
import dev.serenity.module.ModuleManager;
import dev.serenity.ui.hud.HUD;
import dev.serenity.ui.mainmenu.alt.auth.Account;

public class Serenity {
    private static final Serenity instance = new Serenity();
    private String name = "Serenity";
    private final double version = 1.0;

    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private HUD hud;

    public void startClient() {
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        hud = new HUD();

        ConfigManager.init();

        new Account("hawkclone@outlook.com", "Toan1804#");
    }

    public void stopClient() {
        ConfigManager.stop();
    }

    public static Serenity getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVersion() {
        return version;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public HUD getHud() {
        return hud;
    }
}
