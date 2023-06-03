package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.TextSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;

import java.awt.*;

public class TextElement extends SettingElement {
    private final TextSetting textSetting;

    public TextElement(TextSetting textSetting) {
        super(textSetting);
        this.textSetting = textSetting;
        this.settingHeight = 30F;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + settingHeight - Fonts.font20.FONT_HEIGHT;
        Fonts.font20.drawString(textSetting.name, left + 2F, y2, new Color(25, 26, 26).getRGB());
    }
}
