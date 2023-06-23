package dev.serenity.setting.impl;

import dev.serenity.module.Module;
import dev.serenity.setting.Setting;

import java.util.function.Supplier;

public class BooleanSetting extends Setting {
    private boolean state;

    public BooleanSetting(String name, boolean state, Module module) {
        this.name = name;
        this.state = state;
        module.settings.add(this);
        this.canDisplay = () -> true;
    }

    public BooleanSetting(String name, boolean state, Module module, Supplier<Boolean> displayable) {
        this.name = name;
        this.state = state;
        module.settings.add(this);
        this.canDisplay = displayable;
    }

    public void toggle() {
        state = !state;
    }

    public boolean isEnabled() {
        return state;
    }

    public void setEnabled(boolean state) {
        this.state = state;
    }
}
