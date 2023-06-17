package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.math.MathUtils;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class NumberElement extends SettingElement {
    private final NumberSetting numberSetting;
    public NumberElement(NumberSetting numberSetting) {
        super(numberSetting);
        this.numberSetting = numberSetting;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y + 2F, new Color(32, 32, 32).getRGB());
        Fonts.font20.drawString(setting.name, x + 15F, y + 21 - Fonts.font20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());

        float startX = x2 - 133F;
        float endX = x2 - 13F;

        float range = (endX - startX) / ((numberSetting.getMaximum() - numberSetting.getMinimum()) / numberSetting.getIncrement());

        if (Mouse.isButtonDown(0)) {
            for (float i = startX; i <= endX; i += range) {
                if (HoveringUtils.isHovering(mouseX, mouseY, i - 7F, y + 21F - 7F, i + 7F, y + 21F + 7F)) {
                    numberSetting.setValue(MathUtils.round((numberSetting.getIncrement() * (i - startX)) / range + numberSetting.getMinimum(), numberSetting.getIncrement()));
                }
            }
        }

        String text;
        if ((int) numberSetting.getValue() == numberSetting.getValue()) {
            text = String.valueOf( (int) numberSetting.getValue());
        } else {
            text = String.valueOf(numberSetting.getValue());
        }


        Fonts.font20.drawString(text, startX - 20F, y + 21F - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(207, 207, 207).getRGB());

        float currentPosX = startX + ((numberSetting.getValue() - numberSetting.getMinimum()) / numberSetting.getIncrement()) * range;
        RenderUtils.drawRoundedRect(startX, y + 20F, currentPosX, y + 22F, 1F, new Color(76, 194, 255).getRGB());
        RenderUtils.drawRoundedRect(currentPosX, y + 20F, endX, y + 22F, 1F, new Color(159, 159, 159).getRGB());
        RenderUtils.drawFilledCircle(currentPosX, y + 21F, 7F, new Color(69, 69, 69));
        RenderUtils.drawFilledCircle(currentPosX, y + 21F, 3F, new Color(76, 194, 255));
    }
}
