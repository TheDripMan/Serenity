package dev.serenity.ui.clickgui.element;

import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.ui.clickgui.element.module.ModuleElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class SearchElement {
    public int left, top, right, bottom;
    public GuiTextField searchBox;
    public boolean searched = false;

    public SearchElement(int left, int top, int right, int bottom) {
        setPosition(left, top, right, bottom);

        searchBox = new GuiTextField(0, Fonts.font20, left + 7, (top + bottom) / 2 - 5, 120, 20, false, true);
        searchBox.setEnableBackgroundDrawing(false);
        searchBox.setTextColor(new Color(42, 42, 42).getRGB());
    }

    public void drawElement(int mouseX, int mouseY, float width, float height, ArrayList<CategoryElement> categoryElements) {
        RenderUtils.drawRoundedRectWithBorder(left, top, right, bottom, 4F, new Color(250, 252, 253).getRGB(), new Color(224, 230, 234).getRGB(), 0.5F);

        if (searchBox.isFocused()) {
            Gui.drawRect(left + 1F, bottom - 1F, right - 1F, bottom, new Color(0, 103, 192).getRGB());
            searchBox.drawTextBox();
        } else if (searchBox.text.length() == 0) {
            searchBox.text = "Find a module";
            searchBox.drawTextBox();
            searchBox.text = "";
        } else {
            searchBox.drawTextBox();
        }

        if (searched) {
            Fonts.fontBold30.drawString("Search results", this.left, 90F, new Color(25, 26, 26).getRGB());

            float count = this.bottom + 20F;
            int results = 0;
            if (!searchBox.text.isEmpty()) {
                for (CategoryElement categoryElement : categoryElements) {
                    for (ModuleElement moduleElement : categoryElement.moduleElements) {
                        if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                            moduleElement.drawElement(mouseX, mouseY, this.left, count, width - 30F, height);
                            count += 50F;
                            results++;
                        }
                    }
                }
            }

            if (!searchBox.text.isEmpty()) {
                if (results == 0) {
                    Fonts.font20.drawString("No results for " + searchBox.text, this.left, this.bottom + 15F, new Color(25, 26, 26).getRGB());
                }
            }

        }
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton, float width, float height, ArrayList<CategoryElement> categoryElements) {
        searchBox.setFocused(HoveringUtils.isHovering(mouseX, mouseY, left, top, right, bottom));

        if (searched) {
            float count = this.bottom + 20F;
            if (!searchBox.text.isEmpty()) {
                for (CategoryElement categoryElement : categoryElements) {
                    for (ModuleElement moduleElement : categoryElement.moduleElements) {
                        if (moduleElement.module.getName().toLowerCase().startsWith(searchBox.text.toLowerCase())) {
                            setPosition(160, 150, 325, 170);
                            searchBox.xPosition = left + 7;
                            searchBox.yPosition = (top + bottom) / 2 - 5;
                            moduleElement.handleMouseClick(mouseX, mouseY, mouseButton, this.left, count, width - 30F, height);
                            if (Module.selectedModule != null) {
                                searched = false;
                            }
                            count += 50F;
                        }
                    }
                }
            }

        }
    }

    public void handleTyping(char typedChar, int keyCode) {
        if (searchBox.isFocused()) {
            searchBox.textboxKeyTyped(typedChar, keyCode);
            if (keyCode == Keyboard.KEY_RETURN) {
                Module.selectedModule = null;
                Category.selectedCategory = null;
                CategoryElement.settingElements.clear();
                searched = true;
            }
        }
    }

    public void setPosition(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
