package dev.serenity.ui.clickgui.element.module.setting.impl;

import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.other.StringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModeElement extends SettingElement {
    private final ModeSetting modeSetting;
    private final ResourceLocation location = new ResourceLocation("serenity/clickgui/downArrow.png");
    private boolean expanded = false;

    public ModeElement(ModeSetting modeSetting) {
        super(modeSetting);
        this.modeSetting = modeSetting;
    }

    @Override
    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + 45F;
        float x1 = right - 60F - Fonts.font20.getStringWidth(StringUtils.findLongestString(modeSetting.modes));
        float x2 = right - 30F;

        Color backgroundColor = new Color(43, 43, 43);
        if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
            backgroundColor = new Color(50, 50, 50);
        }

        RenderUtils.drawRoundedRect(left - 0.5F, top - 0.5F, right + 0.5F, y2 + 0.5F, 3F, backgroundColor.getRGB());

        Fonts.font20.drawString(setting.name, left + 15F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());

        if (!expanded) {
            Color accentColor = HoveringUtils.isHovering(mouseX, mouseY, x1, (top + y2) / 2 - 10F, x2 , (top + y2) / 2 + 10F) ? new Color(60, 60, 60) : new Color(55, 55, 55);

            RenderUtils.drawRoundedRectWithBorder(x1, (top + y2) / 2 - 10F, x2 , (top + y2) / 2 + 10F, 4F, accentColor.getRGB(), new Color(58, 58, 58).getRGB(), 0.75F);
            Fonts.font20.drawString(modeSetting.getCurrentMode(), x1 + 5F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F , new Color(255, 255, 255).getRGB());
            RenderUtils.drawImage(location, right - 50F, (top + y2) / 2 - 5F, 10F, 10F);

            this.settingHeight = 45F;
        } else {
            float startY = (top + y2) / 2 - 5F;

            float top2 = startY - 7F;
            float bottom2 = startY + 30F * modeSetting.modes.length;

            RenderUtils.drawRoundedRectWithBorder(x1 - 5F, top2,x2 + 5F , bottom2, 4F, new Color(44, 44, 44).getRGB(), new Color(36, 36, 36).getRGB(), 0.5F);

            for (String mode : modeSetting.modes) {
                if (mode.equals(modeSetting.getCurrentMode())) {
                    RenderUtils.drawRoundedRect(x1, startY, x2, startY + 24F, 4F, new Color(56, 56, 56).getRGB());
                    RenderUtils.drawRoundedRect(x1 + 2F, startY + 5F, x1 + 4F, startY + 24F - 5F, 1F, new Color(76, 194, 255).getRGB());
                } else {
                    if (HoveringUtils.isHovering(mouseX, mouseY, x1, startY, x2, startY + 24F)) {
                        RenderUtils.drawRoundedRect(x1, startY, x2, startY + 24F, 4F, new Color(56, 56, 56).getRGB());
                    }
                }

                Fonts.font20.drawString(mode, x1 + 10F, startY + 12F - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
                startY += 30F;
            }

            this.settingHeight = 20F + bottom2 - top2;
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + 45F;
        float x1 = right - 60F - Fonts.font20.getStringWidth(StringUtils.findLongestString(modeSetting.modes));
        float x2 = right - 30F;

        float startY = (top + y2) / 2 - 5F;

        float top2 = startY - 7F;
        float bottom2 = startY + 30F * modeSetting.modes.length;

        if (expanded) {
            if (!HoveringUtils.isHovering(mouseX, mouseY, x1 - 5F, top2,x2 + 5F , bottom2)) {
                expanded = false;
            } else {
                for (String mode : modeSetting.modes) {
                    if (HoveringUtils.isHovering(mouseX, mouseY, x1, startY, x2, startY + 24F)) {
                        modeSetting.setMode(mode);
                        expanded = false;
                    }
                    startY += 30F;
                }
            }
        } else {
            if (HoveringUtils.isHovering(mouseX, mouseY, x1, (top + y2) / 2 - 10F, x2, (top + y2) / 2 + 10F)) {
                expanded = true;
            }
        }
    }
}
