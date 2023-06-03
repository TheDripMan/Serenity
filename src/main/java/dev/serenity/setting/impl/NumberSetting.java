package dev.serenity.setting.impl;

import dev.serenity.module.Module;
import dev.serenity.setting.Setting;

import java.util.function.Supplier;

public class NumberSetting extends Setting {
    private float value, minimum, maximum, increment;

    public NumberSetting(String name, float value, float minimum, float maximum, float increment, Module module) {
        this.name = name;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        module.settings.add(this);
        this.canDisplay = () -> true;
    }

    public NumberSetting(String name, float value, float minimum, float maximum, float increment, Module module, Supplier<Boolean> displayable) {
        this.name = name;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        module.settings.add(this);
        this.canDisplay = displayable;
    }

    public float getValue() {
        return value;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }

    public float getIncrement() {
        return increment;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setMinimum(float minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(float maximum) {
        this.maximum = maximum;
    }

}