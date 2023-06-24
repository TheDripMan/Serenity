package dev.serenity.ui.clickgui.element.module;

import dev.serenity.module.Module;
import dev.serenity.setting.Setting;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.component.ToggleSwitch;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.BooleanElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.ModeElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.NumberElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class ModuleElement {
    public final Module module;
    private final ArrayList<SettingElement> settingElements;
    private final ToggleSwitch toggleSwitch;

    public ModuleElement(Module module) {
        this.module = module;
        this.settingElements = new ArrayList<>();
        this.toggleSwitch = new ToggleSwitch();

        for (Setting setting : module.settings) {
            if (setting instanceof ModeSetting) {
                settingElements.add(new ModeElement((ModeSetting) setting));
            }
            if (setting instanceof NumberSetting) {
                settingElements.add(new NumberElement((NumberSetting) setting));
            }
            if (setting instanceof BooleanSetting) {
                settingElements.add(new BooleanElement((BooleanSetting) setting));
            }
        }
    }

    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        float count = 0;
        if (module.expanded) {
            for (SettingElement settingElement : settingElements) {
                if (settingElement.isDisplayable()) count += settingElement.settingHeight;
            }
            module.height = count;
        }

        RenderUtils.drawRoundedRect(x + 170F, y, x2 - 15F, y2 + 2F, 5F, new Color(45, 45, 45).getRGB());
        Fonts.font20.drawString(module.getName(), x + 180F, y + 20F - Fonts.font20.FONT_HEIGHT / 2F - 3F, Color.WHITE.getRGB());
        Fonts.font18.drawString(module.getDescription(), x + 180F, y + 20F - Fonts.font18.FONT_HEIGHT / 2F + 7F, new Color(208, 208, 208).getRGB());

        toggleSwitch.state = module.isEnabled();

        if (settingElements.size() > 0) {
            if (HoveringUtils.isHovering(mouseX, mouseY,x2 - 33F - 10F, y + 20F - 10F, x2 - 33F + 10F, y + 20F + 10F)) {
                RenderUtils.drawRoundedRect(x2 - 33F - 10F, y + 20F - 10F, x2 - 33F + 10F, y + 20F + 10F, 5F, new Color(35, 35, 35).getRGB());
            }
            RenderUtils.drawImage(module.arrow, x2 - 40F, y + 20F - 7F, 14F, 14F);
            toggleSwitch.onDraw(mouseX, mouseY, x2 - 72F, y + 20F - 6F, 23F, 12F);

            if (module.expanded) {
                float startY = y + 40F;
                for (SettingElement settingElement : settingElements) {
                    if (settingElement.isDisplayable()) {
                        settingElement.drawElement(mouseX, mouseY,x + 170F, startY, x2 - 15F, startY + settingElement.settingHeight);
                        startY += settingElement.settingHeight;
                    }
                }
            }

        } else {
            toggleSwitch.onDraw(mouseX, mouseY, x2 - 47F, y + 20F - 6F, 23F, 12F);
        }
    }

    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        if (settingElements.size() > 0 && HoveringUtils.isHovering(mouseX, mouseY,x2 - 33F - 10F, y + 20F - 10F, x2 - 33F + 10F, y + 20F + 10F)) {
            module.expanded = !module.expanded;

            if (module.expanded) {
                module.arrow = new ResourceLocation("serenity/clickgui/downArrow.png");
            } else {
                module.height = 0F;
                module.arrow = new ResourceLocation("serenity/clickgui/arrow.png");
            }
        }

        if (module.expanded) {
            float startY = y + 40F;
            for (SettingElement settingElement : settingElements) {
                if (settingElement.isDisplayable()) {
                    settingElement.handleMouseClick(mouseX, mouseY,x + 170F, startY, x2 - 15F, startY + settingElement.settingHeight);
                    startY += settingElement.settingHeight;
                }
            }
        }

        if (settingElements.size() > 0) {
            if (HoveringUtils.isHovering(mouseX, mouseY, x2 - 72F, y + 20F - 6F, x2 - 72F + 23F, y + 20F + 6F)) {
                module.toggle();
            }
        } else {
            if (HoveringUtils.isHovering(mouseX, mouseY, x2 - 47F, y + 20F - 6F, x2 - 47F + 23F, y + 20F + 6F)) {
                module.toggle();
            }
        }
    }
}
