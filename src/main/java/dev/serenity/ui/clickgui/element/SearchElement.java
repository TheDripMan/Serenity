package dev.serenity.ui.clickgui.element;

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

public class SearchElement {
    private final SearchBox searchBox;
    private final ResourceLocation searchIcon;
    private float scrollHeight = 0F;

    public SearchElement(int x, int y, int x2, int y2) {
        searchBox = new SearchBox(0, x + 15, (y + y2) / 2 - 12, y + 60, 20);
        searchIcon = new ResourceLocation("serenity/clickgui/search.png");
    }

    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2, ArrayList<CategoryElement> categoryElements, float height, int wheel) {
        RenderUtils.drawRoundedRectWithBorder(x + 15F, (y + y2) / 2 - 10F, x + 150F, (y + y2) / 2 + 10F, 5F, new Color(45, 45, 45).getRGB(), new Color(154, 154, 154).getRGB(), 0.5F);
        RenderUtils.drawImage(searchIcon, x + 135F,(y + y2) / 2 - 5F, 10F, 10F);

        if (searchBox.text.length() == 0) {
            searchBox.text = "Search";
            searchBox.drawTextBox();
            searchBox.text = "";
        } else {
            searchBox.drawTextBox();
            int results = 0;

            Fonts.fontBold30.drawString("Search results", x + 170F, y + 30F, Color.WHITE.getRGB());

            handleScrolling(wheel);

            float startY = y + 60 + scrollHeight;

            Stencil.write(true);

            Gui.drawRect(x + 170F, y + 60, x2 - 15F, height - 60F, new Color(32, 32, 32).getRGB());

            glEnable(GL_STENCIL_TEST);
            glStencilFunc(GL_EQUAL, 1, 0xFF);
            glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);

            for (CategoryElement categoryElement : categoryElements) {
                for (ModuleElement moduleElement : categoryElement.moduleElements) {
                    if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                        moduleElement.drawElement(mouseX, mouseY, x, startY, x2, startY + 40F + moduleElement.module.height);
                        startY += 50F + moduleElement.module.height;
                        results++;
                    }
                }
            }

            glDisable(GL_STENCIL_TEST);

            if (results == 0) {
                Fonts.font20.drawString("No results for " + searchBox.text, x + 170F, y + 55, Color.WHITE.getRGB());
            }
        }

    }

    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2, ArrayList<CategoryElement> categoryElements) {
        searchBox.setFocused(HoveringUtils.isHovering(mouseX, mouseY, x + 15F, (y + y2) / 2 - 11F, x + 150F, (y + y2) / 2 + 10F));

        if (searchBox.text.length() > 0) {
            float startY = y2 + scrollHeight;
            for (CategoryElement categoryElement : categoryElements) {
                for (ModuleElement moduleElement : categoryElement.moduleElements) {
                    if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                        moduleElement.handleMouseClick(mouseX, mouseY, x, startY, x2, startY + 40F);
                        startY += 50F;
                    }
                }
            }
        }
    }

    public void handleTyping(char typedChar, int keyCode) {
        searchBox.textboxKeyTyped(typedChar, keyCode);
    }

    public boolean isTyping() {
        return searchBox.text.length() > 0;
    }

    public void handleScrolling(int wheel) {
        if (wheel != 0) {
            float scrollChange = (wheel > 0) ? 20F : -20F;

            scrollHeight += scrollChange;
            if (scrollHeight > 0) scrollHeight = 0F;
        }
    }
}
