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

        Color backgroundColor = new Color(251, 251, 253);
        if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
            backgroundColor = new Color(245, 246, 249);
        }

        RenderUtils.drawRoundedRectWithBorder(left, top, right, y2, 3F, backgroundColor.getRGB(), new Color(225, 230, 234).getRGB(), 0.5F);

        Fonts.font20.drawString(setting.name, left + 15F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(26, 27, 27).getRGB());

        if (!expanded) {
            Color accentColor = HoveringUtils.isHovering(mouseX, mouseY, x1, (top + y2) / 2 - 10F, x2 , (top + y2) / 2 + 10F) ? new Color(250, 251, 251) : new Color(254, 254, 254);

            RenderUtils.drawRoundedRectWithBorder(x1, (top + y2) / 2 - 10F, x2 , (top + y2) / 2 + 10F, 4F, accentColor.getRGB(), new Color(236, 236, 238).getRGB(), 0.75F);
            Fonts.font20.drawString(modeSetting.getCurrentMode(), x1 + 5F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F , new Color(27, 27, 27).getRGB());
            RenderUtils.drawImage(location, right - 50F, (top + y2) / 2 - 5F, 10F, 10F);

            this.settingHeight = 45F;
        } else {
            float startY = (top + y2) / 2 - 5F;

            float top2 = startY - 7F;
            float bottom2 = startY + 30F * modeSetting.modes.length;

            RenderUtils.drawRoundedRectWithBorder(x1 - 5F, top2,x2 + 5F , bottom2, 4F, new Color(246, 249, 251).getRGB(), new Color(222, 224, 225).getRGB(), 0.5F);

            for (String mode : modeSetting.modes) {
                if (mode.equals(modeSetting.getCurrentMode())) {
                    RenderUtils.drawRoundedRect(x1, startY, x2, startY + 24F, 4F, new Color(240, 240, 240).getRGB());
                    RenderUtils.drawRoundedRect(x1 + 2F, startY + 5F, x1 + 4F, startY + 24F - 5F, 1F, new Color(0, 103, 192).getRGB());
                } else {
                    if (HoveringUtils.isHovering(mouseX, mouseY, x1, startY, x2, startY + 24F)) {
                        RenderUtils.drawRoundedRect(x1, startY, x2, startY + 24F, 4F, new Color(239, 239, 239).getRGB());
                    }
                }

                Fonts.font20.drawString(mode, x1 + 10F, startY + 12F - Fonts.font20.FONT_HEIGHT / 2F + 2F, new Color(25, 25, 26).getRGB());
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
