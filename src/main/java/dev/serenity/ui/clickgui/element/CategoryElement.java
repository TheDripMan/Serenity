package dev.serenity.ui.clickgui.element;

import dev.serenity.Serenity;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.ui.clickgui.element.module.ModuleElement;
import dev.serenity.ui.clickgui.element.module.setting.SettingElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class CategoryElement {
    private final Category category;
    public final ArrayList<ModuleElement> moduleElements = new ArrayList<>();
    public static final ArrayList<SettingElement> settingElements = new ArrayList<>();
    private final ResourceLocation location;

    public CategoryElement(Category category) {
        this.category = category;
        this.location = new ResourceLocation("serenity/clickgui/" + category.getName().toLowerCase() + ".png");

        for (Module module : Serenity.getInstance().getModuleManager().getModules()) {
            if (module.getCategory() == category) {
                moduleElements.add(new ModuleElement(module));
            }
        }
    }

    public void drawElement(int mouseX, int mouseY, float left, float top, float right, float bottom, int wheel) {
        float x1 = left + 10F;
        float x2 = left + 175F;
        float y2 = top + 30F;

        if (HoveringUtils.isHovering(mouseX, mouseY, x1, top, x2, y2)) {
            RenderUtils.drawRoundedRect(x1, top, x2, y2, 6F, new Color(45, 45, 45).getRGB());
        }

        handleScrolling(wheel);

        if (category == Category.selectedCategory) {
            RenderUtils.drawRoundedRect(x1, top, x2, y2, 6F, new Color(45, 45, 45).getRGB());
            RenderUtils.drawImage(location, x1 + 15F - 7F, (top + y2) / 2F - 7F, 14F, 14F);
            Fonts.fontBold20.drawString(category.getName(), x1 + 30F, (top + y2) / 2F - Fonts.fontBold20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
            RenderUtils.drawRoundedRect(x1 + 2F, top + 5F, x1 + 4F, y2 - 5F, 1F, new Color(76, 194, 255).getRGB());

            Color textColor = new Color(255, 255, 255);

            if (Module.selectedModule == null) {
                float startY = 120F + Category.selectedCategory.scrollHeight;
                for (ModuleElement moduleElement : moduleElements) {
                    if (startY >= 120 && startY <= 370) {
                        moduleElement.drawElement(mouseX, mouseY, x2 + 30F, startY, right - 30F, bottom);
                    }
                    startY += 50F;
                }
            } else {
                Fonts.fontBold30.drawString("  >", x2 + 30F + Fonts.fontBold30.getStringWidth(category.getName()), 90F, new Color(204, 204, 204).getRGB());
                Fonts.fontBold30.drawString(Module.selectedModule.getName(), x2 + 30F + Fonts.fontBold30.getStringWidth(category.getName()) + Fonts.fontBold30.getStringWidth("  >  "), 90F, textColor.getRGB());
                if (!HoveringUtils.isHovering(mouseX, mouseY, x2 + 30F, 90F, x2 + 30F + Fonts.fontBold30.getStringWidth(category.getName()), 90F + Fonts.fontBold30.FONT_HEIGHT)) {
                    textColor = new Color(204, 204, 204);
                }

                float startY = 120F + Module.selectedModule.settingScrollHeight;
                for (SettingElement settingElement : settingElements) {
                    if (settingElement.setting.canDisplay.get()) {
                        if (startY >= 120F && startY <= 370F) {
                            settingElement.drawElement(mouseX, mouseY, x2 + 30F, startY, right - 30F, bottom);
                        }
                        startY += settingElement.settingHeight + 5F;
                    }
                }

            }

            Fonts.fontBold30.drawString(category.getName(), x2 + 30F, 90F, textColor.getRGB());
        } else {
            RenderUtils.drawImage(location, x1 + 15F - 7F, (top + y2) / 2F - 7F, 14F, 14F);
            Fonts.fontBold20.drawString(category.getName(), x1 + 30F, (top + y2) / 2F - Fonts.fontBold20.FONT_HEIGHT / 2F + 2F, new Color(255, 255, 255).getRGB());
        }
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton, float left, float top, float right, float bottom) {
        float x1 = left + 10F;
        float x2 = left + 175F;
        float y2 = top + 30F;

        if (HoveringUtils.isHovering(mouseX, mouseY, x1, top, x2, y2)) {
            Category.selectedCategory = category;
            Module.selectedModule = null;
            settingElements.clear();
        }

        if (category == Category.selectedCategory) {
            if (Module.selectedModule != null) {

                float startY = 120F + Module.selectedModule.settingScrollHeight;
                for (SettingElement settingElement : settingElements) {
                    if (settingElement.setting.canDisplay.get()) {
                        if (startY >= 120F && startY <= 370F) {
                            settingElement.handleMouseClick(mouseX, mouseY, x2 + 30F, startY, right - 30F, bottom);
                        }
                        startY += settingElement.settingHeight + 5F;
                    }
                }

            }
        }

        if (Module.selectedModule == null) {
            if (category == Category.selectedCategory) {
                float startY = 120F + Category.selectedCategory.scrollHeight;
                for (ModuleElement moduleElement : moduleElements) {
                    if (startY >= 120F && startY <= 370F) {
                        moduleElement.handleMouseClick(mouseX, mouseY, mouseButton, x2 + 30F, startY, right - 30F, bottom);
                    }
                    startY += 50F;
                }
            }
        } else {
            if (HoveringUtils.isHovering(mouseX, mouseY, x2 + 30F, 90F, x2 + 30F + Fonts.fontBold30.getStringWidth(category.getName()), 90F + Fonts.fontBold30.FONT_HEIGHT)) {
                Module.selectedModule = null;
                settingElements.clear();
            }
        }
    }

    public void handleScrolling(int wheel) {
        if (wheel != 0) {
            float scrollChange = (wheel > 0) ? 20F : -20F;

            if (Module.selectedModule == null) {
                Category.selectedCategory.scrollHeight += scrollChange;
                if (Category.selectedCategory.scrollHeight > 0) Category.selectedCategory.scrollHeight = 0F;
            } else {
                Module.selectedModule.settingScrollHeight += scrollChange;
                if (Module.selectedModule.settingScrollHeight > 0) Module.selectedModule.settingScrollHeight = 0F;
            }

        }
    }
}
