package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.NoteSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;

import java.awt.*;

public class NoteElement extends SettingElement {
    private final NoteSetting noteSetting;

    public NoteElement(NoteSetting noteSetting) {
        super(noteSetting);
        this.noteSetting = noteSetting;
        this.settingHeight = 30F;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + settingHeight - Fonts.font20.FONT_HEIGHT;
        Fonts.font20.drawString(noteSetting.name, left + 2F, y2, new Color(255, 255, 255).getRGB());
    }
}
