package dev.serenity.ui.clickgui;

import dev.serenity.module.Category;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.SearchElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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
        RenderUtils.drawRoundedRect(150F, 60F, 325F + 8F, this.height - 60F, 8F, new Color(32, 32, 32, 100).getRGB());
        Gui.drawRect(325F, 60F, 325F + 8F, this.height - 60F, new Color(32, 32, 32).getRGB());
        RenderUtils.drawRoundedRect(325F, 60F, this.width - 150F, this.height - 60F, 8F, new Color(32, 32, 32).getRGB());

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (150 + 325) / 2;
        int j = this.height / 2 + 30;
        GuiInventory.drawEntityOnScreen(i, j, 70, i - mouseX, j - 50 - mouseY, this.mc.thePlayer);

        RenderUtils.drawRoundedRectWithBorder(150F + 35F, this.height - 60F - 120F, 325F - 35F, this.height - 60F - 90F, 8F, new Color(32, 32, 32).getRGB(), new Color(0, 120, 212).getRGB(), 1F);
        Fonts.fontBold25.drawString("Edit HUD", (150F + 325F) / 2F - Fonts.fontBold30.getStringWidth("Edit HUD") / 2F + 2F, this.height - 165F - Fonts.fontBold30.FONT_HEIGHT / 2F + 2F, new Color(180, 180, 180).getRGB());
        Fonts.fontBold30.drawString(mc.session.getProfile().getName(), (150F + 325F) / 2F - Fonts.fontBold30.getStringWidth(mc.session.getProfile().getName()) / 2F, 100F, Color.WHITE.getRGB());

        if (!searchElement.isTyping()) {
            float startY = 120F;
            for (CategoryElement categoryElement : categoryElements) {
                categoryElement.drawElement(mouseX, mouseY, 325F, startY, this.width - 150F, startY + 25F, this.height, Mouse.getDWheel());
                startY += 30F;
            }
        }

        searchElement.drawElement(mouseX, mouseY, 325F, 60F, this.width - 150F, 120F, categoryElements, this.height, Mouse.getDWheel());
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
