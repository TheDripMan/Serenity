package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.ui.clickgui.element.component.Checkbox;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class BooleanElement extends SettingElement {
    private final Checkbox checkbox;
    private final BooleanSetting booleanSetting;

    public BooleanElement(BooleanSetting booleanSetting) {
        super(booleanSetting);
        checkbox = new Checkbox();
        this.booleanSetting = booleanSetting;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        checkbox.state = booleanSetting.isEnabled();

        Gui.drawRect(x, y, x2, y + 2F, new Color(32, 32, 32).getRGB());
        checkbox.onDraw(mouseX, mouseY, x + 15F, y + 21 - 15F/2, 15F);
        Fonts.font20.drawString(setting.name, x + 40F, y + 21 - Fonts.font20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        if (HoveringUtils.isHovering(mouseX, mouseY, x + 15F, y + 21 - 15F/2, x + 30F, y + 21 + 15F/2)) {
            booleanSetting.toggle();
        }
    }
}
