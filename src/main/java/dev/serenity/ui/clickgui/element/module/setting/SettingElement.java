package dev.serenity.ui.clickgui.element.module.setting;

import dev.serenity.setting.Setting;

public class SettingElement {
    public Setting setting;
    public float settingHeight = 45F;

    public SettingElement(Setting setting) {
        this.setting = setting;
    }

    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {}
    public void handleMouseClick(int mouseX, int mouseY, float left, float top, float right, float bottom) {}
}
