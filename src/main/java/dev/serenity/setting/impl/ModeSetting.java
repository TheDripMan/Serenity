package dev.serenity.setting.impl;

import dev.serenity.module.Module;
import dev.serenity.setting.Setting;

import java.util.Arrays;
import java.util.function.Supplier;

public final class ModeSetting extends Setting {
    public String[] modes;
    private int index;

    public ModeSetting(String name, String[] modes, String defaultMode, Module module) {
        this.name = name;
        this.modes = modes;
        this.index = Arrays.asList(modes).indexOf(defaultMode);
        module.settings.add(this);
        this.canDisplay = () -> true;
    }

    public ModeSetting(String name, String[] modes, String defaultMode, Module module, Supplier<Boolean> displayable) {
        this.name = name;
        this.modes = modes;
        this.index = Arrays.asList(modes).indexOf(defaultMode);
        module.settings.add(this);
        this.canDisplay = displayable;
    }

    public String getCurrentMode() {
        return modes[index];
    }

    public void setMode(String mode) {
        index = Arrays.asList(modes).indexOf(mode);
    }
}
