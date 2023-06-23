package dev.serenity.ui.clickgui.element.module.setting;

import dev.serenity.setting.Setting;

public class SettingElement {
    protected final Setting setting;
    public float settingHeight = 40F;

    public SettingElement(Setting setting) {
        this.setting = setting;
    }

    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {}
    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2) {}

    public boolean isDisplayable() {
        return setting.canDisplay.get();
    }
}
