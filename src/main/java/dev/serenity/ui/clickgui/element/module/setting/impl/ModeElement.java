package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.other.StringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModeElement extends SettingElement {
    private final ModeSetting modeSetting;
    private final ResourceLocation arrow = new ResourceLocation("serenity/clickgui/downArrow.png");
    private boolean expanded = false;

    public ModeElement(ModeSetting modeSetting) {
        super(modeSetting);
        this.modeSetting = modeSetting;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y + 2F, new Color(32, 32, 32).getRGB());
        Fonts.font20.drawString(setting.name, x + 15F, y + 21F - Fonts.font20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());

        float startX = x2 - 37F - Fonts.font20.getStringWidth(StringUtils.findLongestString(modeSetting.modes));
        if (!expanded) {
            Color accentColor = HoveringUtils.isHovering(mouseX, mouseY, startX, y + 21F - 10F, x2 - 13F, (y + y2) / 2 + 10F) ? new Color(60, 60, 60) : new Color(55, 55, 55);

            RenderUtils.drawRoundedRectWithBorder(startX, y + 21F - 10F, x2 - 13F, (y + y2) / 2 + 10F, 4F,accentColor.getRGB(), new Color(58, 58, 58).getRGB(), 0.75F);
            RenderUtils.drawImage(arrow, x2 - 27F, y + 21F - 4F, 8, 8);
            Fonts.font20.drawString(modeSetting.getCurrentMode(), startX + 5F, y + 21F - Fonts.font20.FONT_HEIGHT / 2F, Color.WHITE.getRGB());
            this.settingHeight = 40F;
        } else {
            float startY = y + 21F - 10F;
            float endY = startY + 30F * modeSetting.modes.length;
            this.settingHeight = endY - startY + 20F;

            RenderUtils.drawRoundedRectWithBorder(startX - 5F, startY - 5F,x2 - 13F + 5F , endY, 4F, new Color(44, 44, 44).getRGB(), new Color(36, 36, 36).getRGB(), 0.5F);

            for (String mode : modeSetting.modes) {
                if (mode.equals(modeSetting.getCurrentMode())) {
                    RenderUtils.drawRoundedRect(startX, startY, x2 - 13F, startY + 24F, 4F, new Color(56, 56, 56).getRGB());
                    RenderUtils.drawRoundedRect(startX + 2F, startY + 5F, startX + 4F, startY + 24F - 5F, 1F, new Color(76, 194, 255).getRGB());
                } else {
                    if (HoveringUtils.isHovering(mouseX, mouseY, startX, startY, x2 - 13F, startY + 24F)) {
                        RenderUtils.drawRoundedRect(startX, startY, x2 - 13F, startY + 24F, 4F, new Color(56, 56, 56).getRGB());
                    }
                }

                Fonts.font20.drawString(mode,startX + 10F, startY + 12F - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
                startY += 30F;
            }
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        float startX = x2 - 37F - Fonts.font20.getStringWidth(StringUtils.findLongestString(modeSetting.modes));
        float startY = y + 21F - 10F;
        float endY = startY + 30F * modeSetting.modes.length;


        if (expanded) {
            if (!HoveringUtils.isHovering(mouseX, mouseY, startX - 5F, startY - 5F,x2 - 13F + 5F , endY)) {
                expanded = false;
            } else {
                for (String mode : modeSetting.modes) {
                    if (HoveringUtils.isHovering(mouseX, mouseY, startX, startY, x2 - 13F, startY + 24F)) {
                        modeSetting.setMode(mode);
                        expanded = false;
                    }
                    startY += 30F;
                }
            }
        } else {
            if (HoveringUtils.isHovering(mouseX, mouseY, startX, y + 21F - 10F, x2 - 13F, (y + y2) / 2 + 10F)) {
                expanded = true;
            }
        }

    }
}
