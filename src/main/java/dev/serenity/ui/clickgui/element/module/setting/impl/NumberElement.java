package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class NumberElement extends SettingElement {
    private final NumberSetting numberSetting;

    public NumberElement(NumberSetting numberSetting) {
        super(numberSetting);
        this.numberSetting = numberSetting;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + settingHeight;

        Color backgroundColor = new Color(251, 251, 253);
        if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
            backgroundColor = new Color(245, 246, 249);
        }

        RenderUtils.drawRoundedRectWithBorder(left, top, right, y2, 3F, backgroundColor.getRGB(), new Color(225, 230, 234).getRGB(), 0.5F);

        Fonts.font20.drawString(setting.name, left + 15F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(26, 27, 27).getRGB());

        float startX = right - 150F;
        float endX = right - 30F;
        float range = (endX - startX) / (numberSetting.getMaximum() - numberSetting.getMinimum() + 1F);

        if (Mouse.isButtonDown(0)) {
            for (float i = numberSetting.getMinimum(); i <= numberSetting.getMaximum(); i += numberSetting.getIncrement()) {
                if (HoveringUtils.isHovering(mouseX, mouseY, (startX + range * i) - 7F, (top + y2) / 2 - 7F, (startX + range * i) + 7F, (top + y2) / 2 + 7F)) {
                    numberSetting.setValue(i);
                }
            }
        }

        float currentPosX = startX + range * numberSetting.getValue();

        String text;
        if ((int) numberSetting.getValue() == numberSetting.getValue()) {
            text = String.valueOf( (int) numberSetting.getValue());
        } else {
            text = String.valueOf(numberSetting.getValue());
        }


        Fonts.font20.drawString(text, startX - 20F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(180, 179, 180).getRGB());

        RenderUtils.drawRoundedRect(startX + range, (top + y2) / 2F - 1F, currentPosX, (top + y2) / 2F + 1F, 1F, new Color(14, 106, 186).getRGB());
        RenderUtils.drawRoundedRect(currentPosX, (top + y2) / 2F - 1F, endX, (top + y2) / 2F + 1F, 1F, new Color(139, 139, 140).getRGB());

        RenderUtils.drawFilledCircle(currentPosX, (top + y2) / 2F, 7F, new Color(227, 227, 229));
        RenderUtils.drawFilledCircle(currentPosX, (top + y2) / 2F, 6.5F, new Color(255, 255, 255));
        RenderUtils.drawFilledCircle(currentPosX, (top + y2) / 2F, 3F, new Color(0, 103, 192));

    }
}
