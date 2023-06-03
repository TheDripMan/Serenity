package dev.serenity.ui.clickgui.element.module;

import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.Setting;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NoteSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.BooleanElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.ModeElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.NoteElement;
import dev.serenity.ui.clickgui.element.module.setting.impl.NumberElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModuleElement {
    public final Module module;
    private final ResourceLocation arrow = new ResourceLocation("serenity/clickgui/arrow.png");

    public ModuleElement(Module module) {
        this.module = module;
    }

    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        float y2 = top + 45F;

        Color backgroundColor = new Color(43, 43, 43);
        if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
            backgroundColor = new Color(50, 50, 50);
        }
        if (module.isEnabled()) {
            backgroundColor = new Color(80, 80, 80);
        }

        RenderUtils.drawRoundedRect(left - 0.5F, top - 0.5F, right + 0.5F, y2 + 0.5F, 3F, backgroundColor.getRGB());

        Fonts.font20.drawString(module.getName(), left + 15F, (top + y2) / 2 - Fonts.font20.FONT_HEIGHT + 2F, new Color(255, 255, 255).getRGB());
        Fonts.font18.drawString(module.getDescription(), left + 15F, (top + y2) / 2 + 3F, new Color(208, 208, 208).getRGB());

        RenderUtils.drawImage(arrow, right - 20F, (top + y2) / 2 - 5F, 10F, 10F);
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton, float left, float top, float right, float bottom) {
        float y2 = top + 45F;

        if (mouseButton == 0) {
            if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
                Category.selectedCategory = module.getCategory();
                Module.selectedModule = module;

                for (Setting setting : module.settings) {
                    if (setting instanceof BooleanSetting) {
                        CategoryElement.settingElements.add(new BooleanElement((BooleanSetting) setting));
                    }
                    if (setting instanceof NoteSetting) {
                        CategoryElement.settingElements.add(new NoteElement((NoteSetting) setting));
                    }
                    if (setting instanceof NumberSetting) {
                        CategoryElement.settingElements.add(new NumberElement((NumberSetting) setting));
                    }
                    if (setting instanceof ModeSetting) {
                        CategoryElement.settingElements.add(new ModeElement((ModeSetting) setting));
                    }
                }

            }
        }

        if (mouseButton == 1) {
            if (HoveringUtils.isHovering(mouseX, mouseY, left, top, right, y2)) {
                module.toggle();
            }
        }
    }
}
