package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class NumberElement extends SettingElement {
    public NumberElement(NumberSetting numberSetting) {
        super(numberSetting);
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y + 2F, new Color(32, 32, 32).getRGB());
        Fonts.font20.drawString(setting.name, x + 15F, y + 21 - Fonts.font20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());

        float startX = x2 - 133F;
        float endX = x2 - 13F;

        RenderUtils.drawRoundedRect(startX, y + 20F, endX, y + 22F, 1F, new Color(76, 194, 255).getRGB());
    }
}
