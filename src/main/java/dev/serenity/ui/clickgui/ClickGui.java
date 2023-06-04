package dev.serenity.ui.clickgui;

import dev.serenity.Serenity;
import dev.serenity.module.Category;
import dev.serenity.module.impl.render.ClickGUI;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.SearchElement;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {
    private final ArrayList<CategoryElement> categoryElements = new ArrayList<>();
    private final SearchElement searchElement;

    public ClickGui() {
        for (Category category : Category.values()) {
            categoryElements.add(new CategoryElement(category));
        }
        searchElement = new SearchElement(160, 150, 325, 170);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRoundedRect(150F, 60F, this.width - 150F, this.height - 60F, 4F, new Color(32, 32, 32).getRGB());

        RenderUtils.drawPlayerAvatar(150F, 60, 60 + 90F);

        searchElement.drawElement(mouseX, mouseY, this.width - 150F, this.height - 60F, categoryElements);

        float count = 6F;
        if (searchElement.searched) {
            count = 5F;
            if (Category.selectedCategory != null) {
                count = 6F;
            }
        }

        for (CategoryElement categoryElement : categoryElements) {
            categoryElement.drawElement(mouseX, mouseY, 150F, 30 * count, this.width - 150F, this.height - 60F, Mouse.getDWheel());
            count += 1.2F;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            Serenity.getInstance().getModuleManager().getModule(ClickGUI.class).setState(false);
        }

        searchElement.handleTyping(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        float count = 6F;
        if (searchElement.searched) {
            count = 5F;

            if (Category.selectedCategory != null) {
                count = 6F;
            }
        }

        searchElement.handleMouseClick(mouseX, mouseY, mouseButton, this.width - 150F, this.height - 60F, categoryElements);
        for (CategoryElement categoryElement : categoryElements) {
            categoryElement.handleMouseClick(mouseX, mouseY, mouseButton, 150F, 30 * count, this.width - 150F, this.height - 60F);
            count += 1.2F;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
