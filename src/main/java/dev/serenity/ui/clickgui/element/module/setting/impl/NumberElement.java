package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.math.MathUtils;
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

        Color backgroundColor = new Color(43, 43, 43);
        if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
            backgroundColor = new Color(50, 50, 50);
        }

        RenderUtils.drawRoundedRect(left - 0.5F, top - 0.5F, right + 0.5F, y2 + 0.5F, 3F, backgroundColor.getRGB());

        Fonts.font20.drawString(setting.name, left + 15F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());

        float startX = right - 150F;
        float endX = right - 30F;
        float range = (endX - startX) / ((numberSetting.getMaximum() - numberSetting.getMinimum()) / numberSetting.getIncrement());

        if (Mouse.isButtonDown(0)) {
            for (float i = startX; i < endX; i += range) {
                if (HoveringUtils.isHovering(mouseX, mouseY, i - 7F, (top + y2) / 2F - 7F, i + 7F, (top + y2) / 2 + 7F)) {
                    numberSetting.setValue(MathUtils.round((numberSetting.getIncrement() * (i - startX)) / range + numberSetting.getMinimum(), numberSetting.getIncrement()));
                }
            }
        }

        float currentPosX = startX + ((numberSetting.getValue() - numberSetting.getMinimum()) / numberSetting.getIncrement()) * range;

        String text;
        if ((int) numberSetting.getValue() == numberSetting.getValue()) {
            text = String.valueOf( (int) numberSetting.getValue());
        } else {
            text = String.valueOf(numberSetting.getValue());
        }


        Fonts.font20.drawString(text, startX - 20F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(180, 179, 180).getRGB());

        RenderUtils.drawRoundedRect(startX + range, (top + y2) / 2F - 1F, currentPosX, (top + y2) / 2F + 1F, 1F, new Color(76, 194, 255).getRGB());
        RenderUtils.drawRoundedRect(currentPosX, (top + y2) / 2F - 1F, endX, (top + y2) / 2F + 1F, 1F, new Color(159, 159, 159).getRGB());

        RenderUtils.drawFilledCircle(currentPosX, (top + y2) / 2F, 7F, new Color(69, 69, 69));
        RenderUtils.drawFilledCircle(currentPosX, (top + y2) / 2F, 3F, new Color(76, 194, 255));

    }
}
