package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;

import java.awt.*;

public class BooleanElement extends SettingElement {
    private final BooleanSetting booleanSetting;

    public BooleanElement(BooleanSetting booleanSetting) {
        super(booleanSetting);
        this.booleanSetting = booleanSetting;
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

        if (booleanSetting.isEnabled()) {
            Fonts.font20.drawString("On", right - 85F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
        } else {
            Fonts.font20.drawString("Off", right - 85F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
        }

        drawToggleSwitch(mouseX, mouseY,right - 60F, (top + y2) / 2F - 15F/2, 30F, 15F);
    }

    public void drawToggleSwitch(int mouseX, int mouseY, float x, float y, float width, float height) {
        boolean state = booleanSetting.isEnabled();
        Color circleColor = state ? new Color(0, 0, 0) : new Color(206, 206, 206);
        float circleX = state ? (x + width - (2F + (height - 4F) / 2F)) : (x + (2F + (height - 4F) / 2F));
        Color accentColor = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + width, y + height) ? new Color(76, 194, 255) : new Color(72, 178, 233);
        Color disabledAccentColor = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + width, y + height) ? new Color(39, 39, 39) : new Color(52, 52, 52);
        Color borderColor = new Color(158, 158, 158);

        if (state) {
            RenderUtils.drawRoundedRect(x - 0.5F, y - 0.5F, x + width + 0.5F, y + height + 0.5F, height / 2F, accentColor.getRGB());
        } else {
            RenderUtils.drawRoundedRectWithBorder(x, y, x + width, y + height, height / 2F, disabledAccentColor.getRGB(), borderColor.getRGB() , 0.5F);
        }

        RenderUtils.drawFilledCircle(circleX, y + 2F + (height - 4F) / 2F, (height - 4F) / 2F, circleColor);

    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + settingHeight;
        if (HoveringUtils.isHovering(mouseX, mouseY, right - 60F, (top + y2) / 2F - 15F/2, right - 30F, (top + y2) / 2F + 15F/2)) {
            booleanSetting.toggle();
        }
    }
}
