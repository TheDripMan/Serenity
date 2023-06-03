package dev.serenity.setting.impl;

import dev.serenity.module.Module;
import dev.serenity.setting.Setting;

import java.util.function.Supplier;

public class NoteSetting extends Setting {
    public NoteSetting(String setting, Module module) {
        this.name = setting;
        module.settings.add(this);
        this.canDisplay = () -> true;
    }

    public NoteSetting(String setting, Module module, Supplier<Boolean> displayable) {
        this.name = setting;
        module.settings.add(this);
        this.canDisplay = displayable;
    }
}
