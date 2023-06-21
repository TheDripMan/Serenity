package dev.serenity;

import dev.serenity.auth.Account;
import dev.serenity.command.CommandManager;
import dev.serenity.module.ModuleManager;
import dev.serenity.ui.hud.HUD;

public class Serenity {
    private static final Serenity instance = new Serenity();
    private String name = "Serenity";
    private final double version = 1.0;
    private final ModuleManager moduleManager = new ModuleManager();
    private final CommandManager commandManager = new CommandManager();
    private final HUD hud = new HUD();

    public void startClient() {
        new Account("hawkclone@outlook.com", "Toan1804#").login();
    }

    public void stopClient() {

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
