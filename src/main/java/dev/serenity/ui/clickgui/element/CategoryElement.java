package dev.serenity.ui.clickgui.element;

import dev.serenity.Serenity;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.ui.clickgui.element.module.ModuleElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import dev.serenity.utilities.render.Stencil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class CategoryElement {
    private final Category category;
    private final ResourceLocation iconLocation;
    public final ArrayList<ModuleElement> moduleElements;

    public CategoryElement(Category category) {
        this.category = category;
        this.iconLocation = new ResourceLocation("serenity/clickgui/" + category.getName().toLowerCase() + ".png");

        moduleElements = new ArrayList<>();
        for (Module module : Serenity.getInstance().getModuleManager().getModules()) {
            if (module.getCategory() == category) {
                moduleElements.add(new ModuleElement(module));
            }
        }
    }

    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2, float height, int wheel, float posY) {
        handleScrolling(wheel);

        if (HoveringUtils.isHovering(mouseX, mouseY, x + 15F, y, x + 150F, y2) || Category.selectedCategory == category) {
            RenderUtils.drawRoundedRect(x + 15F, y, x + 150F, y2, 6F, new Color(45, 45, 45).getRGB());

            if (category == Category.selectedCategory) {
                RenderUtils.drawRoundedRect(x + 15F, y + 7F, x + 17F, y2 - 7F, 1F, new Color(76, 194, 255).getRGB());

                Fonts.fontBold30.drawString(category.getName(), x + 170F, posY - 30F, Color.WHITE.getRGB());

                float startY = posY + Category.selectedCategory.scrollHeight;

                Stencil.write(true);

                Gui.drawRect(x + 170F, posY, x2 - 15F, height, new Color(32, 32, 32).getRGB());

                glEnable(GL_STENCIL_TEST);
                glStencilFunc(GL_EQUAL, 1, 0xFF);
                glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);

                for (ModuleElement moduleElement : moduleElements) {
                    moduleElement.drawElement(mouseX, mouseY, x, startY, x2, startY + 40F + moduleElement.module.height);
                    startY += 50F + moduleElement.module.height;
                }

                glDisable(GL_STENCIL_TEST);
            }
        }

        RenderUtils.drawImage(iconLocation, x + 27.5F - 6F, (y + y2) / 2F - 6F, 12F, 12F);
        Fonts.fontBold20.drawString(category.getName(), x + 40F, (y + y2) / 2F - Fonts.fontBold20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());
    }

    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2, float posY, float height) {
        if (HoveringUtils.isHovering(mouseX, mouseY, x + 15F, y, x + 150F, y2)) {
            Category.selectedCategory = category;
        }

        if (Category.selectedCategory == category) {
            float startY = posY + Category.selectedCategory.scrollHeight;
            for (ModuleElement moduleElement : moduleElements) {
                if (HoveringUtils.isHovering(mouseX, mouseY, x + 170F, posY, x2 - 15F, height)) {
                    moduleElement.handleMouseClick(mouseX, mouseY, x, startY, x2, startY + 40F + moduleElement.module.height);
                }
                startY += 50F + moduleElement.module.height;
            }
        }
    }

    public void handleScrolling(int wheel) {
        if (wheel != 0) {
            float scrollChange = (wheel > 0) ? 20F : -20F;

            Category.selectedCategory.scrollHeight += scrollChange;
            if (Category.selectedCategory.scrollHeight > 0) Category.selectedCategory.scrollHeight = 0F;
        }
    }
}
