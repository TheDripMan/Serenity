package dev.serenity.ui.clickgui.element;

import dev.serenity.ui.clickgui.element.module.ModuleElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class SearchElement {
    private final SearchBox searchBox;
    private final ResourceLocation searchIcon;

    public SearchElement(int x, int y, int x2, int y2) {
        searchBox = new SearchBox(0, x + 15, (y + y2) / 2 - 12, 120, 20);
        searchIcon = new ResourceLocation("serenity/clickgui/search.png");
    }

    public void drawElement(int mouseX, int mouseY, float x, float y, float x2, float y2, ArrayList<CategoryElement> categoryElements) {
        RenderUtils.drawRoundedRectWithBorder(x + 15F, (y + y2) / 2 - 10F, x + 150F, (y + y2) / 2 + 10F, 5F, new Color(45, 45, 45).getRGB(), new Color(154, 154, 154).getRGB(), 0.5F);
        RenderUtils.drawImage(searchIcon, x + 135F,(y + y2) / 2 - 5F, 10F, 10F);

        if (searchBox.text.length() == 0) {
            searchBox.text = "Search";
            searchBox.drawTextBox();
            searchBox.text = "";
        } else {
            searchBox.drawTextBox();
            int results = 0;

            Fonts.fontBold30.drawString("Search results", x + 170F, 120F - 30F, Color.WHITE.getRGB());
            for (CategoryElement categoryElement : categoryElements) {
                float startY = 120F;
                for (ModuleElement moduleElement : categoryElement.moduleElements) {
                    if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                        moduleElement.drawElement(mouseX, mouseY, x, startY, x2, moduleElement.module.height);
                        startY += moduleElement.module.height + 10F;
                        results++;
                    }
                }
            }
            if (results == 0) {
                Fonts.font20.drawString("No results for " + searchBox.text, x + 170F, 120F - 5F, Color.WHITE.getRGB());
            }
        }

    }

    public void handleMouseClick(int mouseX, int mouseY, float x, float y, float x2, float y2, ArrayList<CategoryElement> categoryElements) {
        searchBox.setFocused(HoveringUtils.isHovering(mouseX, mouseY, x + 15F, (y + y2) / 2 - 11F, x + 150F, (y + y2) / 2 + 10F));

        if (searchBox.text.length() > 0) {
            for (CategoryElement categoryElement : categoryElements) {
                float startY = 120F;
                for (ModuleElement moduleElement : categoryElement.moduleElements) {
                    if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                        moduleElement.handleMouseClick(mouseX, mouseY, x, startY, x2, moduleElement.module.height);
                        startY += moduleElement.module.height + 10F;
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
}
