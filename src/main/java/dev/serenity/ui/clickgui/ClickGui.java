package dev.serenity.ui.clickgui;

import dev.serenity.module.Category;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.SearchElement;
import dev.serenity.utilities.render.RenderUtils;
import dev.serenity.utilities.render.Stencil;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ClickGui extends GuiScreen {
    private final ArrayList<CategoryElement> categoryElements;
    private final SearchElement searchElement;

    public ClickGui() {
        categoryElements = new ArrayList<>();
        searchElement = new SearchElement(325, 60, this.width - 150, 120);

        for (Category category : Category.values()) {
            categoryElements.add(new CategoryElement(category));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Stencil.write(true);
        RenderUtils.drawRoundedRect(150F, 60F, 325F, this.height - 60F, 8F, new Color(32, 32, 32, 100).getRGB());
        RenderUtils.drawRoundedRect(325F, 60F, this.width - 150F, this.height - 60F, 8F, new Color(32, 32, 32).getRGB());

        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);

        if (!searchElement.isTyping()) {
            float startY = 120F;
            for (CategoryElement categoryElement : categoryElements) {
                categoryElement.drawElement(mouseX, mouseY, 325F, startY, this.width - 150F, startY + 25F, this.height, Mouse.getDWheel());
                startY += 30F;
            }
        }

        searchElement.drawElement(mouseX, mouseY, 325F, 60F, this.width - 150F, 120F, categoryElements);

        glDisable(GL_STENCIL_TEST);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        searchElement.handleMouseClick(mouseX, mouseY, 325F, 60F, this.width - 150F, 120F, categoryElements);

        if (!searchElement.isTyping()) {
            float startY = 120F;
            for (CategoryElement categoryElement : categoryElements) {
                categoryElement.handleMouseClick(mouseX, mouseY, 325F, startY, this.width - 150F, startY + 25F);
                startY += 30F;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }

        searchElement.handleTyping(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
